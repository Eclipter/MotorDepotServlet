package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.TruckDAO;
import by.bsu.dektiarev.entity.Truck;
import by.bsu.dektiarev.entity.TruckStateDTO;
import by.bsu.dektiarev.entity.util.TruckState;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class TruckDAOJPAImpl extends GenericDAOJPAImpl implements TruckDAO {

    private static final String GET_ALL_QUERY = "Truck.getAll";
    private static final String GET_NUMBER_QUERY = "Truck.getNumber";

    private static final Logger LOG = LogManager.getLogger();


    public TruckDAOJPAImpl(EntityManager manager) {
        super(manager);
    }

    @Override
    public List<Truck> getTrucks(int offset, int limit) throws DAOException {
        try {
            TypedQuery<Truck> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, Truck.class);
            namedQuery.setMaxResults(limit);
            namedQuery.setFirstResult(offset);
            return namedQuery.getResultList();
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public Integer getNumberOfTrucks() throws DAOException {
        try {
            TypedQuery<Long> namedQuery = getManager().createNamedQuery(GET_NUMBER_QUERY, Long.class);
            return namedQuery.getSingleResult().intValue();
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public void changeTruckState(int truckId, TruckState truckStateToSet) throws DAOException {
        if (truckStateToSet == null) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        Truck truck = getManager().find(Truck.class, truckId);
        if (truck == null) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        TruckStateDTO truckStateDTO = truck.getState();
        if (truckStateToSet.equals(truckStateDTO.getTruckStateName())) {
            throw new DAOException(ExceptionalMessageKey.TRUCK_HAS_THE_SAME_STATE);
        }

        TruckStateDTO newEntity = getManager().find(TruckStateDTO.class, truckStateToSet.ordinal() + 1);
        if (newEntity == null) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try {
            EntityTransaction transaction = getManager().getTransaction();
            transaction.begin();
            truck.setState(newEntity);
            transaction.commit();
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public Truck addTruck(String number, double capacity) throws DAOException {
        if (capacity < 0 || number == null || number.equals("")) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try {
            EntityTransaction transaction = getManager().getTransaction();
            Truck truck = new Truck();
            transaction.begin();
            truck.setNumber(number);
            truck.setCapacity(capacity);
            TruckStateDTO truckStateDTO = getManager().find(TruckStateDTO.class, TruckState.OK.ordinal() + 1);
            truck.setState(truckStateDTO);
            getManager().persist(truck);
            transaction.commit();
            return truck;
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }
}
