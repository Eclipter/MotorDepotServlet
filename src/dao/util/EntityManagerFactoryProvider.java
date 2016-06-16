package dao.util;

import util.DatabaseConfigurationBundleManager;
import util.DatabaseConfigurationParameterName;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Singleton used to provide EntityManagerFactory to DAO classes
 * Created by USER on 07.03.2016.
 */
public class EntityManagerFactoryProvider {

    private static final String PERSISTENCE_UNIT_NAME = "MotorDepotHibernateManager";

    private static final EntityManagerFactoryProvider OUR_INSTANCE = new EntityManagerFactoryProvider();

    private final EntityManagerFactory factory;

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public static EntityManagerFactoryProvider getInstance() {
        return OUR_INSTANCE;
    }

    private EntityManagerFactoryProvider() {
        if(Boolean.parseBoolean(
                DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USE_JPA))) {
            this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        else {
            this.factory = null;
        }
    }

}