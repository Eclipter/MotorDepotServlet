package by.bsu.dektiarev.dao.util.pool;

import by.bsu.dektiarev.exception.DatabaseConnectionException;
import by.bsu.dektiarev.exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.DatabaseConfigurationBundleManager;
import by.bsu.dektiarev.util.DatabaseConfigurationParameterName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Custom connection pool that provides connections to DAO classes if JDBC is used
 *
 * @see ProxyConnection
 * Created by USER on 15.06.2016.
 */
public class ConnectionPool {

    private static final Logger LOG = LogManager.getLogger();
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
     * Gives connection to a calling DAO object.
     *
     * @return Connection from pool
     * @throws DatabaseConnectionException
     */
    public Connection takeConnection() throws DatabaseConnectionException {
        try {
            if (destroying.get()) {
                LOG.error("requesting database pool while destroying pool");
                throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR);
            }
            ProxyConnection connection = freeConnections.poll(maxWaitingTime, TimeUnit.SECONDS);
            if (connection == null) {
                LOG.error("timed out waiting for pool");
                throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR);
            }
            busyConnections.put(connection);
            if(!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
            return connection;
        } catch (InterruptedException ex) {
            LOG.error("interrupted while retrieving pool from the pool");
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, ex);
        } catch (SQLException ex) {
            LOG.error("error during connection setup");
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, ex);
        }
    }

    /**
     * Returns connection back to the pool
     *
     * @param connection pool to return
     * @throws DatabaseConnectionException
     */
    void returnConnection(ProxyConnection connection) throws DatabaseConnectionException {
        try {
            boolean removed = busyConnections.remove(connection);
            if (!removed) {
                LOG.error("returning wrong pool");
                throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR);
            }
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            LOG.error("interrupted while returning connection back to pool", e);
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, e);
        }
    }

    /**
     * Initializes connection pool
     *
     * @throws DatabaseConnectionException
     */
    public void init() throws DatabaseConnectionException {
        if (freeConnections != null) {
            LOG.warn("rejecting poll initialization: already initialized");
            return;
        }
        LOG.info("initializing pool pool");
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
            LOG.info("pool initialized");
        } catch (InterruptedException ex) {
            LOG.error("interrupted while initializing pool", ex);
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, ex);
        } catch (ClassNotFoundException ex) {
            LOG.error("driver class not found", ex);
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, ex);
        } catch (SQLException ex) {
            LOG.error("sql error while initializing pool", ex);
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, ex);
        } catch (NumberFormatException ex) {
            LOG.error("error while parsing db configuration");
            throw new DatabaseConnectionException(ExceptionalMessage.CONNECTION_ERROR, ex);
        }
    }

    /**
     * Destroys pool
     */
    public void destroy() {
        if (destroying.compareAndSet(false, true)) {
            LOG.info("destroying pool");
            closeConnections(busyConnections);
            closeConnections(freeConnections);
            LOG.info("pool destroyed successfully");
        }
    }

    /**
     * Closes connections in a specified queue
     *
     * @param connections queue of connections
     */
    private void closeConnections(BlockingQueue<ProxyConnection> connections) {
        while (!connections.isEmpty()) {
            try {
                ProxyConnection connection = connections.take();
                if(!connection.getAutoCommit()) {
                    connection.commit();
                }
                connection.realClose();
            } catch (SQLException e) {
                LOG.error("error while closing connections: ", e);
            } catch (InterruptedException e) {
                LOG.error("interrupted while closing connections", e);
            }
        }
    }
}
