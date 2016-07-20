package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.RequestDAO;
import by.bsu.dektiarev.entity.Request;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class RequestDAOJPAImpl extends GenericDAOJPAImpl implements RequestDAO {


    private static final String GET_ALL_QUERY = "Request.getAll";
    private static final String GET_UNASSIGNED_QUERY = "Request.getUnassigned";

    @Override
    public List<Request> getAllRequests() {
        TypedQuery<Request> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, Request.class);
        return namedQuery.getResultList();
    }

    @Override
    public List<Request> getUnassignedRequests() {
        TypedQuery<Request> namedQuery = getManager().createNamedQuery(GET_UNASSIGNED_QUERY, Request.class);
        return namedQuery.getResultList();
    }

    @Override
    public void addNewRequest(int cargoWeight) throws DAOException {
        if(cargoWeight < 0) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            Request request = new Request();
            request.setCargoWeight(cargoWeight);
            getManager().persist(request);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.DML_EXCEPTION);
        } finally {
            getManager().clear();
        }
    }

    @Override
    public void deleteRequest(int requestId) throws DAOException {
        if(requestId <= 0) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        Request request = getManager().find(Request.class, requestId);
        if(request == null) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            getManager().remove(request);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.DML_EXCEPTION);
        } finally {
            getManager().clear();
        }
    }
}
