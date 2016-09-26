package by.bsu.dektiarev.dao.util.pool;

import by.bsu.dektiarev.exception.DatabaseConnectionException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import by.bsu.dektiarev.util.DatabaseConfigurationBundleManager;
import by.bsu.dektiarev.util.DatabaseConfigurationParameterName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private String url;
    private String userName;
    private String password;

    private ConnectionPool() {
    }

    private ProxyConnection createNewConnection() throws SQLException, InterruptedException {
        Connection connection = DriverManager.getConnection(url, userName, password);
        return new ProxyConnection(connection);
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
                throw new DatabaseConnectionException(ExceptionalMessageKey.CONNECTION_ERROR);
            }
            ProxyConnection connection = freeConnections.poll(maxWaitingTime, TimeUnit.SECONDS);
            if (connection == null) {
                LOG.error("timed out waiting for pool");
                throw new DatabaseConnectionException(ExceptionalMessageKey.CONNECTION_ERROR);
            }
            if(!connection.isClosed()) {
                busyConnections.put(connection);
                if(!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                return connection;
            } else {
                LOG.info("Creating new connection");
                ProxyConnection newConnection = createNewConnection();
                busyConnections.put(newConnection);
                return newConnection;
            }
        } catch (InterruptedException ex) {
            LOG.error("interrupted while retrieving pool from the pool");
            throw new DatabaseConnectionException(ExceptionalMessageKey.CONNECTION_ERROR, ex);
        } catch (SQLException ex) {
            LOG.error("error during connection setup");
            throw new DatabaseConnectionException(ExceptionalMessageKey.CONNECTION_ERROR, ex);
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
                LOG.error("returning to wrong pool");
                throw new DatabaseConnectionException(ExceptionalMessageKey.CONNECTION_ERROR);
            }
            if(!connection.isClosed()) {
                freeConnections.put(connection);
            } else {
                LOG.info("Creating new connection");
                ProxyConnection newConnection = createNewConnection();
                freeConnections.put(newConnection);
            }
        } catch (InterruptedException e) {
            LOG.error("interrupted while returning connection back to pool", e);
            throw new DatabaseConnectionException(ExceptionalMessageKey.CONNECTION_ERROR, e);
        } catch (SQLException e) {
            LOG.error("exception while checking connection before returning", e);
            throw new DatabaseConnectionException(ExceptionalMessageKey.CONNECTION_ERROR, e);
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
        LOG.info("initializing pool");
        try {
            final int poolSize = Integer.parseInt(
                    DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.POOL_SIZE));
            this.freeConnections = new ArrayBlockingQueue<>(poolSize);
            this.busyConnections = new ArrayBlockingQueue<>(poolSize);
            url = DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.URL);
            userName = DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USERNAME);
            password = DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.PASSWORD);
            this.maxWaitingTime = Integer.parseInt(
                    DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.MAX_WAITING_TIME));
            Class.forName(DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.DRIVER));
            for (int i = 0; i < poolSize; i++) {
                LOG.info("Creating new connection");
                ProxyConnection connection = createNewConnection();
                freeConnections.put(connection);
            }
            LOG.info("pool initialized");
        } catch (InterruptedException ex) {
            LOG.error("interrupted while initializing pool", ex);
            throw new DatabaseConnectionException(ExceptionalMessageKey.CONNECTION_ERROR, ex);
        } catch (ClassNotFoundException ex) {
            LOG.error("driver class not found", ex);
            throw new DatabaseConnectionException(ExceptionalMessageKey.CONNECTION_ERROR, ex);
        } catch (SQLException ex) {
            LOG.error("sql error while initializing pool", ex);
            throw new DatabaseConnectionException(ExceptionalMessageKey.CONNECTION_ERROR, ex);
        } catch (NumberFormatException ex) {
            LOG.error("error while parsing db configuration");
            throw new DatabaseConnectionException(ExceptionalMessageKey.CONNECTION_ERROR, ex);
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
