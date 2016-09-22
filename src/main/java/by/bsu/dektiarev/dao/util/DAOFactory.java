package by.bsu.dektiarev.dao.util;

import by.bsu.dektiarev.dao.GenericDAO;
import by.bsu.dektiarev.dao.impl.jdbc.*;
import by.bsu.dektiarev.dao.impl.jpa.*;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import by.bsu.dektiarev.util.DatabaseConfigurationBundleManager;
import by.bsu.dektiarev.util.DatabaseConfigurationParameterName;

/**
 * Factory that creates and returns DAO object of a specified type
 * Created by USER on 15.06.2016.
 */
public final class DAOFactory {

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
        switch (type) {
            case DRIVER:
                return useJPA ? new DriverDAOJPAImpl() : new DriverDAOJDBCImpl();
            case REQUEST:
                return useJPA ? new RequestDAOJPAImpl() : new RequestDAOJDBCImpl();
            case TRIP:
                return useJPA ? new TripDAOJPAImpl() : new TripDAOJDBCImpl();
            case TRUCK:
                return useJPA ? new TruckDAOJPAImpl() : new TruckDAOJDBCImpl();
            case USER:
                return useJPA ? new UserDAOJPAImpl() : new UserDAOJDBCImpl();
            case STATION:
                return useJPA ? new StationDAOJPAImpl() : new StationDAOJDBCImpl();
            default:
                throw new DAOException(ExceptionalMessageKey.NO_DAO_CLASS);
        }
    }
}
