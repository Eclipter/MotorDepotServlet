package dao.impl.jpa;

import dao.util.EntityManagerFactoryProvider;

import javax.persistence.EntityManager;

/**
 * Generic DAO class
 * Created by USER on 07.03.2016.
 */
public abstract class GenericDAOJPAImpl {

    private final EntityManager manager;

    public GenericDAOJPAImpl() {
        this.manager = EntityManagerFactoryProvider.getInstance().getFactory().createEntityManager();
    }

    public EntityManager getManager() {
        return manager;
    }
}
