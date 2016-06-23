package by.bsu.dektiarev.dao.util;

import by.bsu.dektiarev.dao.GenericDAO;
import by.bsu.dektiarev.dao.impl.jdbc.*;
import by.bsu.dektiarev.dao.impl.jpa.*;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessage;
import by.bsu.dektiarev.util.DatabaseConfigurationBundleManager;
import by.bsu.dektiarev.util.DatabaseConfigurationParameterName;

/**
 * Factory that creates and returns DAO object of a specified type
 * Created by USER on 15.06.2016.
 */
public final class DAOFactory {

    private static boolean USE_JPA;

    static {
        USE_JPA = Boolean.parseBoolean(
                DatabaseConfigurationBundleManager.getProperty(DatabaseConfigurationParameterName.USE_JPA));
    }

    public static GenericDAO getDAOFromFactory(DAOType type) throws DAOException {
        switch (type) {
            case DRIVER:
                return USE_JPA ? new DriverDAOJPAImpl() : new DriverDAOJDBCImpl();
            case REQUEST:
                return USE_JPA ? new RequestDAOJPAImpl() : new RequestDAOJDBCImpl();
            case TRIP:
                return USE_JPA ? new TripDAOJPAImpl() : new TripDAOJDBCImpl();
            case TRUCK:
                return USE_JPA ? new TruckDAOJPAImpl() : new TruckDAOJDBCImpl();
            case USER:
                return USE_JPA ? new UserDAOJPAImpl() : new UserDAOJDBCImpl();
            default:
                throw new DAOException(ExceptionalMessage.NO_DAO_CLASS);
        }
    }

    private DAOFactory() {
    }
}
