package by.bsu.dektiarev.listener;

import by.bsu.dektiarev.dao.util.EntityManagerProvider;
import by.bsu.dektiarev.dao.util.pool.ConnectionPool;
import by.bsu.dektiarev.exception.DatabaseConnectionException;
import by.bsu.dektiarev.util.DatabaseConfigurationBundleManager;
import by.bsu.dektiarev.util.DatabaseConfigurationParameterName;
import by.bsu.dektiarev.util.RequestParameterName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener used to initialize and destroy connection pool if servlet is running in JDBC mode,
 * or initialize EntityManager, if servlet is running in JPA mode
 * Created by USER on 16.06.2016.
 */
public class MotorDepotServletContextListener implements ServletContextListener {

    private static final Logger LOG = LogManager.getLogger();
    private static final String INITIALIZING_POOL_ERROR = "Initializing pool error";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (!Boolean.parseBoolean(
                DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USE_JPA))) {
            try {
                ConnectionPool.getInstance().init();
            } catch (DatabaseConnectionException e) {
                LOG.error(e);
                servletContextEvent.getServletContext().setAttribute(RequestParameterName.ERROR_MESSAGE,
                        INITIALIZING_POOL_ERROR + ": " + e.getCause().getMessage());
            }
        } else {
            EntityManagerProvider.getInstance().init();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (!Boolean.parseBoolean(
                DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USE_JPA))) {
            ConnectionPool.getInstance().destroy();
        }
        else {
            EntityManagerProvider.getInstance().destroy();
        }
    }
}
