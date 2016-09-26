package by.bsu.dektiarev.dao;

import by.bsu.dektiarev.entity.Driver;
import by.bsu.dektiarev.entity.Request;
import by.bsu.dektiarev.entity.Truck;
import by.bsu.dektiarev.entity.User;
import by.bsu.dektiarev.exception.DAOException;

import java.util.List;

/**
 * DAO interface that operates on DRIVER table
 * Created by USER on 15.06.2016.
 */
public interface DriverDAO extends GenericDAO {

    /**
     * Retrieves drivers that are present in the database, no more than the limit
     *
     * @param offset from which record to start
     * @return drivers list
     * @throws DAOException
     */
    List<Driver> getDrivers(int offset) throws DAOException;

    /**
     * Retrieves all drivers whose trucks are in a good condition
     *
     * @return drivers list
     * @throws DAOException
     */
    List<Driver> getAllDriversWithHealthyTrucks() throws DAOException;

    /**
     * Retrieves the whole number of drivers
     *
     * @return number of drivers
     * @throws DAOException
     */
    Integer getNumberOfDrivers() throws DAOException;

    /**
     * Retrieves the driver by user
     *
     * @param user corresponding user
     * @return driver by.bsu.dektiarev.entity or null if there s no such user or this user is not a driver
     * @throws DAOException
     */
    Driver find(User user) throws DAOException;

    /**
     * Searches for a driver that is currently completing given request
     *
     * @param request corresponding request entity
     * @return list with a driver or empty list if the request is not assigned
     * @throws DAOException
     */
    Driver find(Request request) throws DAOException;

    /**
     * Adds new driver in the database
     *
     * @param truck corresponding truck entity
     * @throws DAOException in case of DML error
     */
    void addDriver(String login, String password, Truck truck) throws DAOException;
}
