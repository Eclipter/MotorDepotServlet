package dao.impl.jpa;

import dao.TruckDAO;
import entity.Truck;
import entity.TruckStateDTO;
import entity.util.TruckState;
import exception.DAOException;
import exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO class used to operate on TRUCK table
 * Created by USER on 08.03.2016.
 */
public class TruckDAOJPAImpl extends GenericDAOJPAImpl implements TruckDAO {

    private static final String GET_ALL_QUERY = "Truck.getAll";

    /**
     * Gets all the trucks
     * @return list of trucks
     */
    @Override
    public List<Truck> getAllTrucks() {
        TypedQuery<Truck> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, Truck.class);
        return namedQuery.getResultList();
    }

    /**
     * Changes truck state
     * @param truckId id of truck
     * @param truckStateToSet state to set
     * @throws DAOException in case of DML error
     */
    @Override
    public void changeTruckState(int truckId, TruckState truckStateToSet) throws DAOException {
        if(truckStateToSet == null) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            Truck truck = getManager().find(Truck.class, truckId);
            if(truck == null) {
                throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
            }
            TruckStateDTO truckStateDTO = truck.getStateByStateId();
            if(truckStateToSet.equals(truckStateDTO.getTruckStateName())) {
                throw new DAOException(ExceptionalMessage.TRUCK_HAS_THE_SAME_STATE);
            }

            TruckStateDTO newEntity = getManager().find(TruckStateDTO.class, truckStateToSet.ordinal() + 1);
            if(newEntity == null) {
                throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
            }
            truck.setStateByStateId(newEntity);
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
    @Override
    public Truck addNewTruck(int capacity) throws DAOException {
        if(capacity < 0) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        EntityTransaction transaction = getManager().getTransaction();
        Truck truck = new Truck();
        try {
            transaction.begin();

            truck.setCapacity(capacity);
            TruckStateDTO truckStateDTO = getManager().find(TruckStateDTO.class, TruckState.OK.ordinal() + 1);
            truck.setStateByStateId(truckStateDTO);
            getManager().persist(truck);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.DML_EXCEPTION);
        } finally {
            getManager().clear();
        }
        return truck;
    }
}
