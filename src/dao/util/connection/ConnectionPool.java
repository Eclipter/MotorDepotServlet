package dao.util.connection;

import exception.DatabaseConnectionException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DatabaseConfigurationBundleManager;
import util.DatabaseConfigurationParameterName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by USER on 15.06.2016.
 */
public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool INSTANCE = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    private AtomicBoolean destroying = new AtomicBoolean();
    private int maxWaitingTime;
    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> busyConnections;

    private ConnectionPool() {
    }

    public Connection takeConnection() throws DatabaseConnectionException {
        try {
            if(destroying.get()) {
                logger.error("requesting database connection while destroying pool");
                throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR);
            }
            ProxyConnection connection = freeConnections.poll(maxWaitingTime, TimeUnit.SECONDS);
            if(connection == null) {
                logger.error("timed out waiting for connection");
                throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR);
            }
            busyConnections.put(connection);
            return connection;
        } catch (InterruptedException ex) {
            logger.error("interrupted while retrieving connection from the pool");
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, ex);
        }
    }

    void returnConnection(ProxyConnection connection) throws DatabaseConnectionException {
        try {
            boolean removed = busyConnections.remove(connection);
            if(!removed) {
                logger.error("returning wrong connection");
                throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR);
            }
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error("interrupted while returning connection back", e);
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, e);
        }
    }

    public void initPool() throws DatabaseConnectionException {
        if(freeConnections != null ) {
            logger.warn("rejecting poll initialization: already initialized");
            return;
        }
        try {
            int poolSize = Integer.parseInt(
                    DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.POOL_SIZE));
            this.freeConnections = new ArrayBlockingQueue<>(poolSize);
            this.busyConnections = new ArrayBlockingQueue<>(poolSize);
            String url = DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.URL);
            String username = DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USERNAME);
            String password = DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.PASSWORD);
            this.maxWaitingTime = Integer.parseInt(
                    DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.MAX_WAITING_TIME));
            Class.forName(DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.DRIVER));
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, username, password);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.put(proxyConnection);
            }
        } catch (InterruptedException | SQLException | ClassNotFoundException | NumberFormatException e) {
            logger.error("error while initializing pool", e);
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, e);
        }
    }

    public void destroyPool() {
        destroying.set(true);
        try {
            for (ProxyConnection connection : busyConnections) {
                connection.realClose();
            }
            for (ProxyConnection connection : freeConnections) {
                connection.realClose();
            }
        } catch (SQLException e) {
            logger.error("error while closing connections: ", e);
        }
    }
}
