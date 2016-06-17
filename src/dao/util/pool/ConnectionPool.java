package dao.util.pool;

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
 * Custom pool pool that provides connections to DAO classes if JDBC id used
 *
 * @see ProxyConnection
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

    /**
     * Gives pool to a calling DAO object.
     *
     * @return Connection from pool
     * @throws DatabaseConnectionException
     */
    public Connection takeConnection() throws DatabaseConnectionException {
        try {
            if (destroying.get()) {
                logger.error("requesting database pool while destroying pool");
                throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR);
            }
            ProxyConnection connection = freeConnections.poll(maxWaitingTime, TimeUnit.SECONDS);
            if (connection == null) {
                logger.error("timed out waiting for pool");
                throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR);
            }
            busyConnections.put(connection);
            return connection;
        } catch (InterruptedException ex) {
            logger.error("interrupted while retrieving pool from the pool");
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, ex);
        }
    }

    /**
     * Returns pool back to pool
     *
     * @param connection pool to return
     * @throws DatabaseConnectionException
     */
    void returnConnection(ProxyConnection connection) throws DatabaseConnectionException {
        try {
            boolean removed = busyConnections.remove(connection);
            if (!removed) {
                logger.error("returning wrong pool");
                throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR);
            }
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error("interrupted while returning pool back", e);
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, e);
        }
    }

    /**
     * Initializes pool
     *
     * @throws DatabaseConnectionException
     */
    public void init() throws DatabaseConnectionException {
        if (freeConnections != null) {
            logger.warn("rejecting poll initialization: already initialized");
            return;
        }
        logger.info("initializing pool pool");
        try {
            final int poolSize = Integer.parseInt(
                    DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.POOL_SIZE));
            this.freeConnections = new ArrayBlockingQueue<>(poolSize);
            this.busyConnections = new ArrayBlockingQueue<>(poolSize);
            final String url = DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.URL);
            final String username = DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USERNAME);
            final String password = DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.PASSWORD);
            this.maxWaitingTime = Integer.parseInt(
                    DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.MAX_WAITING_TIME));
            Class.forName(DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.DRIVER));
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, username, password);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.put(proxyConnection);
            }
            logger.info("pool initialized");
        } catch (InterruptedException ex) {
            logger.error("interrupted while initializing pool", ex);
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, ex);
        } catch (ClassNotFoundException ex) {
            logger.error("driver class not found", ex);
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, ex);
        } catch (SQLException ex) {
            logger.error("sql error while initializing pool", ex);
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, ex);
        }
    }

    /**
     * Destroys pool
     */
    public void destroy() {
        if (destroying.compareAndSet(false, true)) {
            logger.info("destroying pool");
            for (ProxyConnection connection : busyConnections) {
                try {
                    connection.realClose();
                } catch (SQLException e) {
                    logger.error("error while closing connections: ", e);
                }
            }
            for (ProxyConnection connection : freeConnections) {
                try {
                    connection.realClose();
                } catch (SQLException e) {
                    logger.error("error while closing connections: ", e);
                }
            }
            logger.info("pool destroyed successfully");
        }
    }
}
