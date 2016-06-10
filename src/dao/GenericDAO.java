package dao;

import dao.util.EntityManagerFactoryProvider;

import javax.persistence.EntityManager;

/**
 * Generic DAO class
 * Created by USER on 07.03.2016.
 */
public abstract class GenericDAO {

    private final EntityManager manager;

    public GenericDAO() {
        this.manager = EntityManagerFactoryProvider.getInstance().getFactory().createEntityManager();
    }

    public EntityManager getManager() {
        return manager;
    }
}
