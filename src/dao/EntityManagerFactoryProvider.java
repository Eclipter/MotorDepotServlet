package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by USER on 07.03.2016.
 */
public class EntityManagerFactoryProvider {
    private EntityManagerFactory factory;

    public EntityManagerFactory getFactory() {
        return factory;
    }

    private static EntityManagerFactoryProvider ourInstance = new EntityManagerFactoryProvider();

    public static EntityManagerFactoryProvider getInstance() {
        return ourInstance;
    }

    private EntityManagerFactoryProvider() {
        initFactory();
    }

    private void initFactory() {
        this.factory = Persistence.createEntityManagerFactory("MotorDepotHibernateManager");
    }
}