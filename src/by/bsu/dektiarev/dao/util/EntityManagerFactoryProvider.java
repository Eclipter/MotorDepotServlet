package by.bsu.dektiarev.dao.util;

import by.bsu.dektiarev.util.DatabaseConfigurationBundleManager;
import by.bsu.dektiarev.util.DatabaseConfigurationParameterName;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton used to provide EntityManagerFactory to DAO classes
 * Created by USER on 07.03.2016.
 */
public class EntityManagerFactoryProvider {

    private static final String PERSISTENCE_UNIT_NAME = "MotorDepotHibernateManager";
    private static final String URL_PROPERTY = "hibernate.connection.url";
    private static final String DRIVER_PROPERTY = "hibernate.connection.driver_class";
    private static final String USERNAME_PROPERTY = "hibernate.connection.username";
    private static final String PASSWORD_PROPERTY = "hibernate.connection.password";
    private static final String POOL_SIZE_PROPERTY = "hibernate.connection.pool_size";

    private static final EntityManagerFactoryProvider INSTANCE = new EntityManagerFactoryProvider();

    private final EntityManagerFactory factory;

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public static EntityManagerFactoryProvider getInstance() {
        return INSTANCE;
    }

    private EntityManagerFactoryProvider() {
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
            this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
        }
        else {
            this.factory = null;
        }
    }

}