package dao.impl.jpa;

import dao.GenericDAO;
import dao.util.EntityManagerFactoryProvider;

import javax.persistence.EntityManager;

public abstract class GenericDAOJPAImpl implements GenericDAO {

    private final EntityManager manager;

    public GenericDAOJPAImpl() {
        this.manager = EntityManagerFactoryProvider.getInstance().getFactory().createEntityManager();
    }

    public EntityManager getManager() {
        return manager;
    }
}
