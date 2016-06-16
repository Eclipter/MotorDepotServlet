package dao;

import entity.Truck;
import entity.util.TruckState;
import exception.DAOException;

import java.util.List;

/**
 * Created by USER on 15.06.2016.
 */
public interface TruckDAO extends GenericDAO {

    List<Truck> getAllTrucks() throws DAOException;
    void changeTruckState(int truckId, TruckState truckStateToSet) throws DAOException;
    Truck addNewTruck(int capacity) throws DAOException;
}
