package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.TruckDAO;
import by.bsu.dektiarev.entity.Truck;
import by.bsu.dektiarev.entity.TruckStateDTO;
import by.bsu.dektiarev.entity.util.TruckState;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class TruckDAOJPAImpl extends GenericDAOJPAImpl implements TruckDAO {

    private static final String GET_ALL_QUERY = "Truck.getAll";

    @Override
    public List<Truck> getAllTrucks() {
        TypedQuery<Truck> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, Truck.class);
        return namedQuery.getResultList();
    }

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
            TruckStateDTO truckStateDTO = truck.getState();
            if(truckStateToSet.equals(truckStateDTO.getTruckStateName())) {
                throw new DAOException(ExceptionalMessage.TRUCK_HAS_THE_SAME_STATE);
            }

            TruckStateDTO newEntity = getManager().find(TruckStateDTO.class, truckStateToSet.ordinal() + 1);
            if(newEntity == null) {
                throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
            }
            truck.setState(newEntity);
            transaction.commit();
        } finally {
            getManager().clear();
        }
    }

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
            truck.setState(truckStateDTO);
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
