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
 * DAO class used to operate on REQUEST table
 * Created by USER on 12.03.2016.
 */
public class RequestDAO extends GenericDAO {

    private static final String REQUEST_PARAMETER = "request";
    private static final String GET_ALL_QUERY = "RequestEntity.getAll";
    private static final String SEARCH_FOR_DRIVER_QUERY = "RequestEntity.searchForDriver";

    /**
     * Gets all the requests
     * @return request list
     */
    public List<RequestEntity> getAllRequests() {
        TypedQuery<RequestEntity> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, RequestEntity.class);
        return namedQuery.getResultList();
    }

    /**
     * Gets all unassigned requests
     * @return request list
     */
    public List<RequestEntity> getUnassignedRequests() {
        TypedQuery<RequestEntity> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, RequestEntity.class);
        List<RequestEntity> requestEntityList = namedQuery.getResultList();
        List<RequestEntity> unsetRequests = new ArrayList<>();
        for(RequestEntity requestEntity : requestEntityList) {
            List<DriverEntity> driverEntityList = searchForDriverCompletingRequest(requestEntity);
            if(driverEntityList.isEmpty()) {
                unsetRequests.add(requestEntity);
            }
        }
        return unsetRequests;
    }

    /**
     * Searcher for a driver that is currently completing their request
     * @param requestEntity corresponding request entity
     * @return list with a driver or empty list if the request is not assigned
     */
    public List<DriverEntity> searchForDriverCompletingRequest(RequestEntity requestEntity) {
        TypedQuery<DriverEntity> namedQuery = getManager().createNamedQuery(SEARCH_FOR_DRIVER_QUERY, DriverEntity.class);
        namedQuery.setParameter(REQUEST_PARAMETER, requestEntity);
        return namedQuery.getResultList();
    }

    /**
     * Adds new request to the database
     * @param cargoWeight cargo weight parameter
     * @throws DAOException in case of DML error
     */
    public void addNewRequest(int cargoWeight) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setCargoWeight(cargoWeight);
            getManager().persist(requestEntity);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.DML_EXCEPTION);
        } finally {
            getManager().clear();
        }
    }
}
