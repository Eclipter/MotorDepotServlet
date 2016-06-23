package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.GenericDAO;
import by.bsu.dektiarev.dao.util.EntityManagerFactoryProvider;

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
