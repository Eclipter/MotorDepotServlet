package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.TripDAO;
import by.bsu.dektiarev.entity.*;
import by.bsu.dektiarev.entity.util.TruckState;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class TripDAOJPAImpl extends GenericDAOJPAImpl implements TripDAO {

    private static final String GET_ALL_QUERY = "Trip.getAll";

    @Override
    public List<Trip> getAllTrips() {
        TypedQuery<Trip> query = getManager().createNamedQuery(GET_ALL_QUERY, Trip.class);
        return query.getResultList();
    }

    @Override
    public List<Trip> getTripsByDriver(int driverId) throws DAOException {
        Driver driver = getManager().find(Driver.class, driverId);
        if(driver == null) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        return driver.getTripList();
    }

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
            Truck truck = driver.getTruck();
            int capacity = truck.getCapacity();
            if (cargoWeight > capacity) {
                throw new DAOException(ExceptionalMessage.WEIGHT_MORE_THAN_CAPACITY);
            }
            TruckStateDTO truckStateDTO = truck.getState();
            if (!TruckState.OK.equals(truckStateDTO.getTruckStateName())) {
                throw new DAOException(ExceptionalMessage.TRUCK_NOT_OK);
            }

            Trip trip = new Trip();
            trip.setIsComplete(false);
            trip.setDriver(driver);
            trip.setRequest(request);
            getManager().persist(trip);
            transaction.commit();
        } finally {
            getManager().clear();
        }
    }

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
