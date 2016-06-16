package dao;

import entity.Trip;
import exception.DAOException;

import java.util.List;

/**
 * Created by USER on 15.06.2016.
 */
public interface TripDAO extends GenericDAO {

    List<Trip> getAllTrips() throws DAOException;
    List<Trip> getTripsByDriver(int driverId) throws DAOException;
    void assignDriverToATrip(int requestId, int driverId) throws DAOException;
    void changeTripState(Integer tripId, boolean state) throws DAOException;
}
