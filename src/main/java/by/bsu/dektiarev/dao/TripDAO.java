package by.bsu.dektiarev.dao;

import by.bsu.dektiarev.entity.Trip;
import by.bsu.dektiarev.exception.DAOException;

import java.util.List;

/**
 * DAO interface used to operate on TRIP table
 * Created by USER on 15.06.2016.
 */
public interface TripDAO extends GenericDAO {

    /**
     * Gets all the trips.
     * @return list of trips
     * @throws DAOException
     */
    List<Trip> getAllTrips() throws DAOException;

    /**
    * Get trips by driver id
    * @param driverId driver id
    * @return list of trips
     * @throws DAOException
    */
    List<Trip> getTripsByDriver(int driverId) throws DAOException;

    /**
     * Assigns driver to a trip
     * @param requestId id of request
     * @param driverId id of driver
     * @throws DAOException in case of DML error
     */
    void assignDriverToATrip(int requestId, int driverId) throws DAOException;

    /**
     * Changes the state of a trip
     * @param tripId id of trip
     * @param state state to set
     * @throws DAOException in case of DML error
     */
    void changeTripState(Integer tripId, boolean state) throws DAOException;
}
