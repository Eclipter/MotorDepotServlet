package dao;

import entity.util.TruckState;
import entity.TruckStateEntity;
import entity.TruckEntity;
import exception.DAOException;
import exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO class used to operate on TRUCK table
 * Created by USER on 08.03.2016.
 */
public class TruckDAO extends GenericDAO {

    private static final String GET_ALL_QUERY = "TruckEntity.getAll";

    /**
     * Gets all the trucks
     * @return list of trucks
     */
    public List<TruckEntity> getAllTrucks() {
        TypedQuery<TruckEntity> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, TruckEntity.class);
        return namedQuery.getResultList();
    }

    /**
     * Changes truck state
     * @param truckId id of truck
     * @param truckStateToSet state to set
     * @throws DAOException in case of DML error
     */
    public void changeTruckState(int truckId, TruckState truckStateToSet) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            TruckEntity truckEntity = getManager().find(TruckEntity.class, truckId);
            TruckStateEntity truckStateEntity = truckEntity.getStateByStateId();
            if(truckStateToSet.equals(truckStateEntity.getTruckStateName())) {
                throw new DAOException(ExceptionalMessage.TRUCK_HAS_THE_SAME_STATE);
            }

            TruckStateEntity newEntity = getManager().find(TruckStateEntity.class, truckStateToSet.ordinal() + 1);
            truckEntity.setStateByStateId(newEntity);
            transaction.commit();
        } finally {
            getManager().clear();
        }
    }

    /**
     * Add new truck to the database
     * @param capacity capacity parameter
     * @return resulting truck entity
     * @throws DAOException in case of DML error
     */
    public TruckEntity addNewTruck(int capacity) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        TruckEntity truckEntity = new TruckEntity();
        try {
            transaction.begin();

            truckEntity.setCapacity(capacity);
            TruckStateEntity truckStateEntity = getManager().find(TruckStateEntity.class, TruckState.OK.ordinal() + 1);
            truckEntity.setStateByStateId(truckStateEntity);
            getManager().persist(truckEntity);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.DML_EXCEPTION);
        } finally {
            getManager().clear();
        }
        return truckEntity;
    }
}
