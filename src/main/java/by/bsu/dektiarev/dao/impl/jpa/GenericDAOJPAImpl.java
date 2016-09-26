package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.GenericDAO;

import javax.persistence.EntityManager;

public abstract class GenericDAOJPAImpl implements GenericDAO {

    private final EntityManager manager;

    public GenericDAOJPAImpl(EntityManager manager) {
        this.manager = manager;
    }

    EntityManager getManager() {
        return manager;
    }
}
