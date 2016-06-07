package dao;

import javax.persistence.EntityManager;

/**
 * Created by USER on 07.03.2016.
 */
public abstract class DAOMotorDepot {
    private EntityManager manager;

    public DAOMotorDepot() {
        this.manager = EntityManagerFactoryProvider.getInstance().getFactory().createEntityManager();
    }

    public EntityManager getManager() {
        return manager;
    }
}
