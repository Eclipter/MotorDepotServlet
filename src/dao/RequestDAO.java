package dao;

import entity.DriverEntity;
import entity.RequestEntity;
import exception.DAOException;
import exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 12.03.2016.
 */
public class RequestDAO extends MotorDepotDAO {

    private static final String REQUEST_PARAMETER = "request";

    public List<RequestEntity> getAllRequests() {
        TypedQuery<RequestEntity> namedQuery = getManager().createNamedQuery("RequestEntity.getAll", RequestEntity.class);
        return namedQuery.getResultList();
    }

    public List<RequestEntity> getUnassignedRequests() {
        TypedQuery<RequestEntity> namedQuery = getManager().createNamedQuery("RequestEntity.getAll", RequestEntity.class);
        List<RequestEntity> requestEntityList = namedQuery.getResultList();
        List<RequestEntity> unsetApplications = new ArrayList<>();
        for(RequestEntity requestEntity : requestEntityList) {
            List<DriverEntity> driverEntityList = searchForDriverCompletingRequest(requestEntity);
            if(driverEntityList.isEmpty()) {
                unsetApplications.add(requestEntity);
            }
        }
        return unsetApplications;
    }

    public List<DriverEntity> searchForDriverCompletingRequest(RequestEntity requestEntity) {
        TypedQuery<DriverEntity> namedQuery = getManager().createNamedQuery("RequestEntity.searchForDriver", DriverEntity.class);
        namedQuery.setParameter(REQUEST_PARAMETER, requestEntity);
        return namedQuery.getResultList();
    }

    public void registerNewRequest(int cargoWeight) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setCargoWeight(cargoWeight);
            getManager().persist(requestEntity);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.SQL_EXCEPTION);
        } finally {
            getManager().clear();
        }
    }
}
