package dao;

import entity.Driver;
import entity.Request;
import entity.Truck;
import entity.User;
import exception.DAOException;

import java.util.List;

/**
 * Created by USER on 15.06.2016.
 */
public interface DriverDAO extends GenericDAO {

    List<Driver> getAllDrivers() throws DAOException;
    List<Driver> getDriversWithHealthyTrucks() throws DAOException;
    Driver searchByUser(User user) throws DAOException;
    Driver searchByRequest(Request request) throws DAOException;
    void registerNewDriver(User user, Truck truck) throws DAOException;
}
