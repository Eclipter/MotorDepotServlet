package dao;

import entity.Driver;
import entity.Request;
import entity.Truck;
import entity.User;
import exception.DAOException;

import java.util.List;

/**
 * DAO interface that operates on DRIVER table
 * Created by USER on 15.06.2016.
 */
public interface DriverDAO extends GenericDAO {

    /**
     * Gets all drivers that are present in the database
     * @return drivers list
     * @throws DAOException
     */
    List<Driver> getAllDrivers() throws DAOException;

    /**
     * Gets all drivers whose trucks are in a good condition
     * @return drivers list
     * @throws DAOException
     */
    List<Driver> getDriversWithHealthyTrucks() throws DAOException;

    /**
     * Searches driver by user
     * @param user corresponding user
     * @return driver entity or null if there s no such user or this user is not a driver
     * @throws DAOException
     */
    Driver searchByUser(User user) throws DAOException;

    /**
     * Searches for a driver that is currently completing given request
     * @param request corresponding request entity
     * @return list with a driver or empty list if the request is not assigned
     * @throws DAOException
     */
    Driver searchByRequest(Request request) throws DAOException;

    /**
     * Adds new driver in the database
     * @param user registered user entity
     * @param truck corresponding truck entity
     * @throws DAOException in case of DML error
     */
    void registerNewDriver(User user, Truck truck) throws DAOException;
}
