package dao;

import entity.Request;
import exception.DAOException;

import java.util.List;

/**
 * DAO interface used to operate on REQUEST table
 * Created by USER on 15.06.2016.
 */
public interface RequestDAO extends GenericDAO {

    /**
     * Gets all the requests
     * @return request list
     * @throws DAOException
     */
    List<Request> getAllRequests() throws DAOException;

    /**
     * Gets all unassigned requests
     * @return request list
     * @throws DAOException
     */
    List<Request> getUnassignedRequests() throws DAOException;

    /**
     * Adds new request to the database
     * @param cargoWeight cargo weight parameter
     * @throws DAOException in case of DML error
     */
    void addNewRequest(int cargoWeight) throws DAOException;
}
