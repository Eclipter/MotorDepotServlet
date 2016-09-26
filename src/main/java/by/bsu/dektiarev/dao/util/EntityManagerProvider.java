package by.bsu.dektiarev.dao.util;

import by.bsu.dektiarev.exception.DatabaseConnectionException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import by.bsu.dektiarev.util.DatabaseConfigurationBundleManager;
import by.bsu.dektiarev.util.DatabaseConfigurationParameterName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Singleton used to provide EntityManagerFactory to DAO classes
 * Created by USER on 07.03.2016.
 */
public class EntityManagerProvider {

    private static final Logger LOG = LogManager.getLogger();

    private static final String PERSISTENCE_UNIT_NAME = "MotorDepotHibernateManager";
    private static final String URL_PROPERTY = "hibernate.connection.url";
    private static final String DRIVER_PROPERTY = "hibernate.connection.driver_class";
    private static final String USERNAME_PROPERTY = "hibernate.connection.username";
    private static final String PASSWORD_PROPERTY = "hibernate.connection.password";
    private static final String POOL_SIZE_PROPERTY = "hibernate.connection.pool_size";

    private static final EntityManagerProvider INSTANCE = new EntityManagerProvider();

    private EntityManager manager;
    private AtomicBoolean destroying = new AtomicBoolean();

    public static EntityManagerProvider getInstance() {
        return INSTANCE;
    }

    private EntityManagerProvider() {
    }

    public void init() {
        if(manager != null) {
            LOG.warn("rejecting manager initialization: already initialized");
            return;
        }
        if(Boolean.parseBoolean(
                DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USE_JPA))) {
            final String url = DatabaseConfigurationBundleManager.getProperty(
                    DatabaseConfigurationParameterName.URL);
            final String driver = DatabaseConfigurationBundleManager.getProperty(
                    DatabaseConfigurationParameterName.DRIVER);
            final String userName = DatabaseConfigurationBundleManager.getProperty(
                    DatabaseConfigurationParameterName.USERNAME);
            final String password = DatabaseConfigurationBundleManager.getProperty(
                    DatabaseConfigurationParameterName.PASSWORD);
            final int poolSize = Integer.parseInt(
                    DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.POOL_SIZE));
            Map<String, Object> properties = new HashMap<>();
            properties.put(URL_PROPERTY, url);
            properties.put(DRIVER_PROPERTY, driver);
            properties.put(USERNAME_PROPERTY, userName);
            properties.put(PASSWORD_PROPERTY, password);
            properties.put(POOL_SIZE_PROPERTY, poolSize);
            EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
            this.manager = factory.createEntityManager();
        }
        else {
            this.manager = null;
        }
    }

    public void destroy() {
        EntityManagerFactory factory = manager.getEntityManagerFactory();
        manager.close();
        factory.close();
    }

    public EntityManager getManager() throws DatabaseConnectionException {
        if (destroying.get()) {
            LOG.error("requesting entity manager while destroying manager and factory");
            throw new DatabaseConnectionException(ExceptionalMessageKey.CONNECTION_ERROR);
        }
        return manager;
    }


}