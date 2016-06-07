package dao;

import entity.TruckEntity;
import entity.DriverEntity;
import entity.State;
import entity.StateEntity;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by USER on 08.03.2016.
 */
public class DAOTruck extends DAOMotorDepot {

    static Logger logger = LogManager.getLogger();

    public List<TruckEntity> getAllTrucks() {
        TypedQuery<TruckEntity> namedQuery = getManager().createNamedQuery("TruckEntity.getAll", TruckEntity.class);
        return namedQuery.getResultList();
    }

    public List<TruckEntity> getTruckByDriver(DriverEntity driverEntity) {
        TypedQuery<TruckEntity> namedQuery = getManager().createNamedQuery("TruckEntity.getByDriver", TruckEntity.class);
        namedQuery.setParameter("driver", driverEntity);
        return namedQuery.getResultList();
    }

    /**
     * Gets list of repairing trucks
     *
     * @return list of autos
     */
    public List<TruckEntity> getRepairingTrucks() {
        logger.info("Executing SELECT statement.");
        StateEntity stateEntity = getManager().find(StateEntity.class, State.UNDER_REPAIR.ordinal() + 1);
        return stateEntity.getTrucksById();
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
            logger.info("Executing SELECT statement.");
            TruckEntity truckEntity = getManager().find(TruckEntity.class, truckId);
            StateEntity stateEntity = truckEntity.getStateByStateId();
            if(stateToSet.equals(stateEntity.getStateName())) {
                throw new DAOException(ExceptionalMessage.TRUCK_HAS_THE_SAME_STATE);
            }

            logger.info("Executing UPDATE statement.");
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

            logger.info("Executing INSERT statement.");
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
