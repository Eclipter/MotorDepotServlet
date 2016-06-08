package dao.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by USER on 07.03.2016.
 */
public class EntityManagerFactoryProvider {

    private static final EntityManagerFactoryProvider OUR_INSTANCE = new EntityManagerFactoryProvider();

    private final EntityManagerFactory factory;

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public static EntityManagerFactoryProvider getInstance() {
        return OUR_INSTANCE;
    }

    private EntityManagerFactoryProvider() {
        this.factory = Persistence.createEntityManagerFactory("MotorDepotHibernateManager");
    }

}