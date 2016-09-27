package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.TripDAO;
import by.bsu.dektiarev.entity.*;
import by.bsu.dektiarev.entity.util.TruckState;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class TripDAOJPAImpl extends GenericDAOJPAImpl implements TripDAO {

    private static final String GET_ALL_QUERY = "Trip.getAll";
    private static final String GET_NUMBER_QUERY = "Trip.getNumberOfAll";

    private static final Logger LOG = LogManager.getLogger();

    public TripDAOJPAImpl(EntityManager manager) {
        super(manager);
    }

    @Override
    public List<Trip> getTrips(int offset, int limit) throws DAOException {
        try {
            TypedQuery<Trip> query = getManager().createNamedQuery(GET_ALL_QUERY, Trip.class);
            query.setMaxResults(limit);
            query.setFirstResult(offset);
            return query.getResultList();
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public List<Trip> getTripsByDriver(int driverId, int offset, int limit) throws DAOException {
        try {
            Driver driver = getManager().find(Driver.class, driverId);
            if (driver == null) {
                throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
            }
            List<Trip> tripList = driver.getTripList();
            int toIndex = offset + limit;
            if (toIndex > tripList.size()) {
                toIndex = tripList.size();
            }
            return tripList.subList(offset, toIndex);
        } catch (DAOException ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public Integer getNumberOfTrips() throws DAOException {
        try {
            TypedQuery<Long> namedQuery = getManager().createNamedQuery(GET_NUMBER_QUERY, Long.class);
            return namedQuery.getSingleResult().intValue();
        } catch (Exception ex) {
            LOG.error(ex);
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
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public void addTrip(int requestId, int driverId) throws DAOException {
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
        try {
            EntityTransaction transaction = getManager().getTransaction();
            transaction.begin();
            Trip trip = new Trip();
            trip.setIsComplete(false);
            trip.setDriver(driver);
            trip.setRequest(request);
            getManager().persist(trip);
            transaction.commit();
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public void changeTripState(int tripId, boolean state) throws DAOException {
        Trip trip = getManager().find(Trip.class, tripId);
        if (trip == null) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        if (state == trip.getIsComplete()) {
            throw new DAOException(ExceptionalMessageKey.TRIP_HAS_THIS_STATE);
        }
        try {
            EntityTransaction transaction = getManager().getTransaction();
            transaction.begin();
            trip.setIsComplete(state);
            transaction.commit();
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }
}
