package dao;

import entity.State;
import entity.StateEntity;
import entity.TruckEntity;
import exception.DAOException;
import exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by USER on 08.03.2016.
 */
public class TruckDAO extends MotorDepotDAO {

    private static final String DRIVER_PARAMETER = "driver";

    public List<TruckEntity> getAllTrucks() {
        TypedQuery<TruckEntity> namedQuery = getManager().createNamedQuery("TruckEntity.getAll", TruckEntity.class);
        return namedQuery.getResultList();
    }

    /**
     * Sets truck on repair
     *
     * @param truckId
     * @throws DAOException
     */
    public void changeTruckState(int truckId, State stateToSet) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            TruckEntity truckEntity = getManager().find(TruckEntity.class, truckId);
            StateEntity stateEntity = truckEntity.getStateByStateId();
            if(stateToSet.equals(stateEntity.getStateName())) {
                throw new DAOException(ExceptionalMessage.TRUCK_HAS_THE_SAME_STATE);
            }

            StateEntity newEntity = getManager().find(StateEntity.class, stateToSet.ordinal() + 1);
            truckEntity.setStateByStateId(newEntity);
            transaction.commit();
        } finally {
            getManager().clear();
        }
    }

    public TruckEntity addNewTruck(int capacity) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        TruckEntity truckEntity = new TruckEntity();
        try {
            transaction.begin();

            truckEntity.setCapacity(capacity);
            StateEntity stateEntity = getManager().find(StateEntity.class, State.OK.ordinal() + 1);
            truckEntity.setStateByStateId(stateEntity);
            getManager().persist(truckEntity);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.SQL_EXCEPTION);
        } finally {
            getManager().clear();
        }
        return truckEntity;
    }
}
