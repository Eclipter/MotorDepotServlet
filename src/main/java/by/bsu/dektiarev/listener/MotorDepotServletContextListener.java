package by.bsu.dektiarev.listener;

import by.bsu.dektiarev.dao.GenericDAO;
import by.bsu.dektiarev.dao.util.EntityManagerFactoryProvider;
import by.bsu.dektiarev.dao.util.pool.ConnectionPool;
import by.bsu.dektiarev.exception.DatabaseConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.DatabaseConfigurationBundleManager;
import by.bsu.dektiarev.util.DatabaseConfigurationParameterName;
import by.bsu.dektiarev.util.RequestParameterName;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener used to initialize and destroy connection pool if servlet is running in JDBC mode,
 * or initialize EntityManagerFactory, if servlet is running in JPA mode
 * Created by USER on 16.06.2016.
 */
public class MotorDepotServletContextListener implements ServletContextListener {

    private static final Logger LOG = LogManager.getLogger();
    private static final String INITIALIZING_POOL_ERROR = "Initializing pool error";

    private void setCollectionFetchLimitParameter(ServletContext context) {
        context.setAttribute(RequestParameterName.FETCH_LIMIT, GenericDAO.COLLECTION_QUERY_LIMIT);
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (!Boolean.parseBoolean(
                DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USE_JPA))) {
            try {
                ConnectionPool.getInstance().init();
            } catch (DatabaseConnectionException e) {
                LOG.error(e);
                servletContextEvent.getServletContext().setAttribute(RequestParameterName.ERROR_MESSAGE,
                        INITIALIZING_POOL_ERROR);
            }
        } else {
            EntityManagerFactoryProvider.getInstance();
        }
        setCollectionFetchLimitParameter(servletContextEvent.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (!Boolean.parseBoolean(
                DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USE_JPA))) {
            ConnectionPool.getInstance().destroy();
        }
    }
}
