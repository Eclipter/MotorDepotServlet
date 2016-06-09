package dao;

import dao.util.EntityManagerFactoryProvider;

import javax.persistence.EntityManager;

/**
 * Created by USER on 07.03.2016.
 */
public abstract class MotorDepotDAO {

    private final EntityManager manager;

    public MotorDepotDAO() {
        this.manager = EntityManagerFactoryProvider.getInstance().getFactory().createEntityManager();
    }

    public EntityManager getManager() {
        return manager;
    }
}
