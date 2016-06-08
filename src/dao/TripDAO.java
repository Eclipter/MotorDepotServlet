package dao;

import entity.*;
import exception.DAOException;
import exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by USER on 07.03.2016.
 */
public class TripDAO extends MotorDepotDAO {

    /**
     * Get all trips method.
     *
     * @return list of trips
     */
    public List<TripEntity> getAllTrips() {
        TypedQuery<TripEntity> query = getManager().createNamedQuery("TripEntity.getAll", TripEntity.class);
        return query.getResultList();
    }

    /**
     * Get trips by driver id
     *
     * @param driverId
     * @return list of trips
     */
    public List<TripEntity> getTripsByDriver(int driverId) {
        DriverEntity driverEntity = getManager().find(DriverEntity.class, driverId);
        return driverEntity.getTripsByUserId();
    }

    /**
     * Sets driver on a trip
     *
     * @param applicationId
     * @param driverId
     * @throws DAOException
     */
    public void setDriverOnTrip(int applicationId, int driverId) throws DAOException {

        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            RequestEntity requestEntity = getManager().find(RequestEntity.class, applicationId);
            int cargoWeight = requestEntity.getCargoWeight();

            DriverEntity driverEntity = getManager().find(DriverEntity.class, driverId);
            TruckEntity truckEntity = driverEntity.getTruckByTruckId();
            int capacity = truckEntity.getCapacity();
            if (cargoWeight > capacity) {
                throw new DAOException(ExceptionalMessage.WEIGHT_MORE_THAN_CAPACITY);
            }
            StateEntity stateEntity = truckEntity.getStateByStateId();
            if (!State.OK.equals(stateEntity.getStateName())) {
                throw new DAOException(ExceptionalMessage.TRUCK_NOT_OK);
            }

            TripEntity tripEntity = new TripEntity();
            tripEntity.setIsComplete(false);
            tripEntity.setDriverByDriverUserId(driverEntity);
            tripEntity.setRequestByRequestId(requestEntity);
            getManager().persist(tripEntity);
            transaction.commit();
        } finally {
            getManager().clear();
        }
    }

    public void changeTripState(Integer tripId, boolean state) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        transaction.begin();
        TripEntity tripEntity = getManager().find(TripEntity.class, tripId);
        if(state == tripEntity.getIsComplete()) {
            throw new DAOException(ExceptionalMessage.TRIP_HAS_THIS_STATE);
        }

        tripEntity.setIsComplete(state);
        transaction.commit();
    }
}
