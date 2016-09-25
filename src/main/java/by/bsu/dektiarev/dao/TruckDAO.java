package by.bsu.dektiarev.dao;

import by.bsu.dektiarev.entity.Truck;
import by.bsu.dektiarev.entity.util.TruckState;
import by.bsu.dektiarev.exception.DAOException;

import java.util.List;

/**
 * DAO interface used to operate on TRUCK table
 * Created by USER on 15.06.2016.
 */
public interface TruckDAO extends GenericDAO {

    /**
     * Gets all the trucks
     * @return list of trucks
     * @throws DAOException
     */
    List<Truck> getTrucks(int offset) throws DAOException;

    /**
     * Gets truck by driver ID
     * @param driverId
     * @return corresponding truck
     * @throws DAOException
     */
    Truck getTruckByDriver(int driverId) throws DAOException;

    /**
     * Gets number of trucks
     * @return number of trucks
     * @throws DAOException
     */
    Integer getNumberOfTrucks() throws DAOException;

    /**
     * Changes truck state
     * @param truckId id of truck
     * @param truckStateToSet state to set
     * @throws DAOException in case of DML error
     */
    void changeTruckState(int truckId, TruckState truckStateToSet) throws DAOException;

    /**
     * Add new truck to the database
     * @param number truck number
     * @param capacity capacity parameter
     * @return resulting truck by.bsu.dektiarev.entity
     * @throws DAOException in case of DML error
     */
    Truck addTruck(String number, double capacity) throws DAOException;
}
