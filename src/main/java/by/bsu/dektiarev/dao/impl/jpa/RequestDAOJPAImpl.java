package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.RequestDAO;
import by.bsu.dektiarev.entity.Request;
import by.bsu.dektiarev.entity.Station;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class RequestDAOJPAImpl extends GenericDAOJPAImpl implements RequestDAO {


    private static final String GET_ALL_QUERY = "Request.getAll";
    private static final String GET_UNASSIGNED_QUERY = "Request.getUnassigned";
    private static final String GET_NUMBER_OF_ALL_QUERY = "Request.getNumberOfAll";
    private static final String GET_NUMBER_OF_UNASSIGNED_QUERY = "Truck.getNumberOfUnassigned";

    @Override
    public List<Request> getAllRequests(Integer offset) {
        TypedQuery<Request> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, Request.class);
        namedQuery.setMaxResults(COLLECTION_QUERY_LIMIT);
        namedQuery.setFirstResult(offset);
        return namedQuery.getResultList();
    }

    @Override
    public List<Request> getUnassignedRequests() {
        TypedQuery<Request> namedQuery = getManager().createNamedQuery(GET_UNASSIGNED_QUERY, Request.class);
        return namedQuery.getResultList();
    }

    @Override
    public List<Request> getUnassignedRequests(Integer offset) throws DAOException {
        TypedQuery<Request> namedQuery = getManager().createNamedQuery(GET_UNASSIGNED_QUERY, Request.class);
        namedQuery.setMaxResults(COLLECTION_QUERY_LIMIT);
        namedQuery.setFirstResult(offset);
        return namedQuery.getResultList();
    }

    @Override
    public Integer getNumberOfAllRequests() throws DAOException {
        TypedQuery<Long> namedQuery = getManager().createNamedQuery(GET_NUMBER_OF_ALL_QUERY, Long.class);
        return namedQuery.getSingleResult().intValue();
    }

    @Override
    public Integer getNumberOfUnassignedRequests() throws DAOException {
        TypedQuery<Long> namedQuery = getManager().createNamedQuery(GET_NUMBER_OF_UNASSIGNED_QUERY, Long.class);
        return namedQuery.getSingleResult().intValue();
    }

    @Override
    public void addNewRequest(int departurePointId, int destinationPointId, double cargoWeight) throws DAOException {
        if(cargoWeight < 0 || departurePointId <= 0 || destinationPointId <= 0) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        Station departureStation = getManager().find(Station.class, departurePointId);
        Station destinationStation = getManager().find(Station.class, destinationPointId);
        if(departureStation == null || destinationStation == null) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            Request request = new Request();
            request.setCargoWeight(cargoWeight);
            request.setDepartureStation(departureStation);
            request.setDestinationStation(destinationStation);
            getManager().persist(request);
            transaction.commit();
        } catch (java.lang.Exception ex) {
            throw new DAOException(ExceptionalMessageKey.DML_EXCEPTION);
        } finally {
            getManager().clear();
        }
    }

    @Override
    public void deleteRequest(int requestId) throws DAOException {
        if(requestId <= 0) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        Request request = getManager().find(Request.class, requestId);
        if(request == null) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            getManager().remove(request);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessageKey.DML_EXCEPTION);
        } finally {
            getManager().clear();
        }
    }
}
