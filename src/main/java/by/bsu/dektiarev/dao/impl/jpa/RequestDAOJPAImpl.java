package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.RequestDAO;
import by.bsu.dektiarev.entity.Request;
import by.bsu.dektiarev.entity.Station;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class RequestDAOJPAImpl extends GenericDAOJPAImpl implements RequestDAO {


    private static final String GET_ALL_QUERY = "Request.getAll";
    private static final String GET_UNASSIGNED_QUERY = "Request.getUnassigned";
    private static final String GET_NUMBER_OF_ALL_QUERY = "Request.getNumberOfAll";
    private static final String GET_NUMBER_OF_UNASSIGNED_QUERY = "Truck.getNumberOfUnassigned";

    public RequestDAOJPAImpl(EntityManager manager) {
        super(manager);
    }

    @Override
    public List<Request> getRequests(int offset) throws DAOException {
        try {
            TypedQuery<Request> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, Request.class);
            namedQuery.setMaxResults(COLLECTION_FETCH_LIMIT);
            namedQuery.setFirstResult(offset);
            return namedQuery.getResultList();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public List<Request> getUnassignedRequests() throws DAOException {
        try {
            TypedQuery<Request> namedQuery = getManager().createNamedQuery(GET_UNASSIGNED_QUERY, Request.class);
            return namedQuery.getResultList();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public List<Request> getUnassignedRequests(int offset) throws DAOException {
        try {
            TypedQuery<Request> namedQuery = getManager().createNamedQuery(GET_UNASSIGNED_QUERY, Request.class);
            namedQuery.setMaxResults(COLLECTION_FETCH_LIMIT);
            namedQuery.setFirstResult(offset);
            return namedQuery.getResultList();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public Integer getNumberOfAllRequests() throws DAOException {
        try {
            TypedQuery<Long> namedQuery = getManager().createNamedQuery(GET_NUMBER_OF_ALL_QUERY, Long.class);
            return namedQuery.getSingleResult().intValue();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public Integer getNumberOfUnassignedRequests() throws DAOException {
        try {
            TypedQuery<Long> namedQuery = getManager().createNamedQuery(GET_NUMBER_OF_UNASSIGNED_QUERY, Long.class);
            return namedQuery.getSingleResult().intValue();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public void addRequest(int departurePointId, int destinationPointId, double cargoWeight) throws DAOException {
        if (cargoWeight < 0 || departurePointId <= 0 || destinationPointId <= 0) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        Station departureStation = getManager().find(Station.class, departurePointId);
        Station destinationStation = getManager().find(Station.class, destinationPointId);
        if (departureStation == null || destinationStation == null) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try {
            EntityTransaction transaction = getManager().getTransaction();
            transaction.begin();
            Request request = new Request();
            request.setCargoWeight(cargoWeight);
            request.setDepartureStation(departureStation);
            request.setDestinationStation(destinationStation);
            getManager().persist(request);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public void deleteRequest(int requestId) throws DAOException {
        if (requestId <= 0) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        Request request = getManager().find(Request.class, requestId);
        if (request == null) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try {
            EntityTransaction transaction = getManager().getTransaction();
            transaction.begin();
            getManager().remove(request);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }
}
