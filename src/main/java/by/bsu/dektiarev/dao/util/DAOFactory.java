package by.bsu.dektiarev.dao.util;

import by.bsu.dektiarev.dao.GenericDAO;
import by.bsu.dektiarev.dao.impl.jdbc.*;
import by.bsu.dektiarev.dao.impl.jpa.*;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.DatabaseConnectionException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import by.bsu.dektiarev.util.DatabaseConfigurationBundleManager;
import by.bsu.dektiarev.util.DatabaseConfigurationParameterName;

import javax.persistence.EntityManager;

/**
 * Factory that creates and returns DAO object of a specified type
 * Created by USER on 15.06.2016.
 */
public class DAOFactory {

    private static final DAOFactory INSTANCE = new DAOFactory();

    public static DAOFactory getInstance() {
        return INSTANCE;
    }

    private final boolean useJPA;

    private DAOFactory() {
        useJPA = Boolean.parseBoolean(
                DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USE_JPA));
    }

    public GenericDAO getDAOFromFactory(DAOType type) throws DAOException {
        if (useJPA) {
            EntityManager manager;
            try {
                manager = EntityManagerProvider.getInstance().getManager();
            } catch (DatabaseConnectionException ex) {
                throw new DAOException(ex);
            }
            switch (type) {
                case DRIVER:
                    return new DriverDAOJPAImpl(manager);
                case REQUEST:
                    return new RequestDAOJPAImpl(manager);
                case TRIP:
                    return new TripDAOJPAImpl(manager);
                case TRUCK:
                    return new TruckDAOJPAImpl(manager);
                case USER:
                    return new UserDAOJPAImpl(manager);
                case STATION:
                    return new StationDAOJPAImpl(manager);
                default:
                    throw new DAOException(ExceptionalMessageKey.NO_DAO_CLASS);
            }
        } else {
            switch (type) {
                case DRIVER:
                    return new DriverDAOJDBCImpl();
                case REQUEST:
                    return new RequestDAOJDBCImpl();
                case TRIP:
                    return new TruckDAOJDBCImpl();
                case TRUCK:
                    return new TruckDAOJDBCImpl();
                case USER:
                    return new UserDAOJDBCImpl();
                case STATION:
                    return new StationDAOJDBCImpl();
                default:
                    throw new DAOException(ExceptionalMessageKey.NO_DAO_CLASS);
            }
        }
    }
}
