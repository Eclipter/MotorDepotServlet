package dao.impl.jpa;

import dao.TripDAO;
import entity.*;
import entity.util.TruckState;
import exception.DAOException;
import exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO class used to operate on TRIP table
 * Created by USER on 07.03.2016.
 */
public class TripDAOJPAImpl extends GenericDAOJPAImpl implements TripDAO {

    private static final String GET_ALL_QUERY = "Trip.getAll";

    /**
     * Gets all the trips.
     * @return list of trips
     */
    @Override
    public List<Trip> getAllTrips() {
        TypedQuery<Trip> query = getManager().createNamedQuery(GET_ALL_QUERY, Trip.class);
        return query.getResultList();
    }

    /**
     * Get trips by driver id
     * @param driverId driver id
     * @return list of trips
     */
    @Override
    public List<Trip> getTripsByDriver(int driverId) throws DAOException {
        Driver driver = getManager().find(Driver.class, driverId);
        if(driver == null) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        return driver.getTripsByUserId();
    }

    /**
     * Assigns driver to a trip
     * @param requestId id of request
     * @param driverId id of driver
     * @throws DAOException in case of DML error
     */
    @Override
    public void assignDriverToATrip(int requestId, int driverId) throws DAOException {

        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            Request request = getManager().find(Request.class, requestId);
            if(request == null) {
                throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
            }
            int cargoWeight = request.getCargoWeight();

            Driver driver = getManager().find(Driver.class, driverId);
            if(driver == null) {
                throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
            }
            Truck truck = driver.getTruckByTruckId();
            int capacity = truck.getCapacity();
            if (cargoWeight > capacity) {
                throw new DAOException(ExceptionalMessage.WEIGHT_MORE_THAN_CAPACITY);
            }
            TruckStateDTO truckStateDTO = truck.getStateByStateId();
            if (!TruckState.OK.equals(truckStateDTO.getTruckStateName())) {
                throw new DAOException(ExceptionalMessage.TRUCK_NOT_OK);
            }

            Trip trip = new Trip();
            trip.setIsComplete(false);
            trip.setDriverByDriverUserId(driver);
            trip.setRequestByRequestId(request);
            getManager().persist(trip);
            transaction.commit();
        } finally {
            getManager().clear();
        }
    }

    /**
     * Changes the state of a trip
     * @param tripId id of trip
     * @param state state to set
     * @throws DAOException in case of DML error
     */
    @Override
    public void changeTripState(Integer tripId, boolean state) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        transaction.begin();
        Trip trip = getManager().find(Trip.class, tripId);
        if(trip == null) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        if(state == trip.getIsComplete()) {
            throw new DAOException(ExceptionalMessage.TRIP_HAS_THIS_STATE);
        }

        trip.setIsComplete(state);
        transaction.commit();
    }
}
