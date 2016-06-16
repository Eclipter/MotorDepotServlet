package dao.impl.jpa;

import dao.DriverDAO;
import entity.Driver;
import entity.Request;
import entity.Truck;
import entity.User;
import exception.DAOException;
import exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO class that operates on DRIVER table
 * Created by USER on 21.04.2016.
 */
public class DriverDAOJPAImpl extends GenericDAOJPAImpl implements DriverDAO {

    private static final String GET_ALL_QUERY = "Driver.getAll";
    private static final String GET_DRIVERS_HEALTHY_TRUCKS_QUERY = "Driver.getDriversWithHealthyTrucks";
    private static final String SEARCH_FOR_DRIVER_QUERY = "Driver.searchByRequest";
    private static final String REQUEST_PARAMETER = "request";

    /**
     * Gets all drivers that are present in the database
     * @return drivers list
     */
    @Override
    public List<Driver> getAllDrivers() {
        TypedQuery<Driver> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, Driver.class);
        return namedQuery.getResultList();
    }

    /**
     * Gets all drivers whose trucks are in a good condition
     * @return drivers list
     */
    @Override
    public List<Driver> getDriversWithHealthyTrucks() {
        TypedQuery<Driver> namedQuery = getManager().createNamedQuery(GET_DRIVERS_HEALTHY_TRUCKS_QUERY,
                Driver.class);
        return namedQuery.getResultList();
    }

    /**
     * Searches driver by user
     * @param user corresponding user
     * @return driver entity or null if there s no such user or this user is not a driver
     */
    @Override
    public Driver searchByUser(User user) {
        return getManager().find(Driver.class, user.getId());
    }

    /**
     * Searches for a driver that is currently completing given request
     * @param request corresponding request entity
     * @return list with a driver or empty list if the request is not assigned
     */
    @Override
    public Driver searchByRequest(Request request) {
        TypedQuery<Driver> namedQuery = getManager().createNamedQuery(SEARCH_FOR_DRIVER_QUERY, Driver.class);
        namedQuery.setParameter(REQUEST_PARAMETER, request);
        List<Driver> driverList = namedQuery.getResultList();
        if(driverList.isEmpty()) {
            return null;
        } else {
            return driverList.get(0);
        }
    }

    /**
     * Adds new driver in the database
     * @param user registered user entity
     * @param truck corresponding truck entity
     * @throws DAOException in case of DML error
     */
    @Override
    public void registerNewDriver(User user, Truck truck) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            Driver driver = new Driver();
            driver.setUserId(user.getId());
            driver.setUserByUserId(user);
            driver.setTruckByTruckId(truck);
            getManager().persist(driver);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.DML_EXCEPTION);
        } finally {
            getManager().clear();
        }
    }
}
