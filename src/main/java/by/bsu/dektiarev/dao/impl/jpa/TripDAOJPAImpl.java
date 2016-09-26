package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.TripDAO;
import by.bsu.dektiarev.entity.*;
import by.bsu.dektiarev.entity.util.TruckState;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class TripDAOJPAImpl extends GenericDAOJPAImpl implements TripDAO {

    private static final String GET_ALL_QUERY = "Trip.getAll";
    private static final String GET_NUMBER_QUERY = "Trip.getNumberOfAll";

    public TripDAOJPAImpl(EntityManager manager) {
        super(manager);
    }

    @Override
    public List<Trip> getTrips(int offset) {
        TypedQuery<Trip> query = getManager().createNamedQuery(GET_ALL_QUERY, Trip.class);
        query.setMaxResults(COLLECTION_FETCH_LIMIT);
        query.setFirstResult(offset);
        return query.getResultList();
    }

    @Override
    public List<Trip> getTripsByDriver(int driverId, int offset) throws DAOException {
        try {
            Driver driver = getManager().find(Driver.class, driverId);
            if (driver == null) {
                throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
            }
            List<Trip> tripList = driver.getTripList();
            int toIndex = offset + COLLECTION_FETCH_LIMIT;
            if (toIndex > tripList.size()) {
                toIndex = tripList.size();
            }
            return tripList.subList(offset, toIndex);
        } catch (DAOException ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public Integer getNumberOfTrips() throws DAOException {
        try {
            TypedQuery<Long> namedQuery = getManager().createNamedQuery(GET_NUMBER_QUERY, Long.class);
            return namedQuery.getSingleResult().intValue();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public Integer getNumberOfTripsByDriver(int driverId) throws DAOException {
        try {
            Driver driver = getManager().find(Driver.class, driverId);
            if (driver == null) {
                throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
            }
            return driver.getTripList().size();
        } catch (DAOException ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public void addTrip(int requestId, int driverId) throws DAOException {
        try {
            EntityTransaction transaction = getManager().getTransaction();
            transaction.begin();
            Request request = getManager().find(Request.class, requestId);
            if (request == null) {
                throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
            }
            double cargoWeight = request.getCargoWeight();

            Driver driver = getManager().find(Driver.class, driverId);
            if (driver == null) {
                throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
            }
            Truck truck = driver.getTruck();
            double capacity = truck.getCapacity();
            if (cargoWeight > capacity) {
                throw new DAOException(ExceptionalMessageKey.WEIGHT_MORE_THAN_CAPACITY);
            }
            TruckStateDTO truckStateDTO = truck.getState();
            if (!TruckState.OK.equals(truckStateDTO.getTruckStateName())) {
                throw new DAOException(ExceptionalMessageKey.TRUCK_NOT_OK);
            }
            Trip trip = new Trip();
            trip.setIsComplete(false);
            trip.setDriver(driver);
            trip.setRequest(request);
            getManager().persist(trip);
            transaction.commit();
        } catch (DAOException ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public void changeTripState(int tripId, boolean state) throws DAOException {
        try {
            EntityTransaction transaction = getManager().getTransaction();
            transaction.begin();
            Trip trip = getManager().find(Trip.class, tripId);
            if (trip == null) {
                throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
            }
            if (state == trip.getIsComplete()) {
                throw new DAOException(ExceptionalMessageKey.TRIP_HAS_THIS_STATE);
            }
            trip.setIsComplete(state);
            transaction.commit();
        } catch (DAOException ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }
}
