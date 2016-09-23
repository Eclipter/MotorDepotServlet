package by.bsu.dektiarev.dao;

import by.bsu.dektiarev.entity.Request;
import by.bsu.dektiarev.exception.DAOException;

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
     * @param offset
     */
    List<Request> getAllRequests(Integer offset) throws DAOException;

    /**
     * Gets all unassigned requests
     * @return request list
     * @throws DAOException
     */
    List<Request> getUnassignedRequests() throws DAOException;

    /**
     * Gets all unassigned requests
     * @return request list
     * @throws DAOException
     */
    List<Request> getUnassignedRequests(Integer offset) throws DAOException;

    Integer getNumberOfAllRequests() throws DAOException;

    Integer getNumberOfUnassignedRequests() throws DAOException;

    /**
     * Adds new request to the database
     * @param departurePointId
     * @param destinationPointId
     * @param cargoWeight cargo weight parameter
     * @throws DAOException in case of DML error
     */
    void addNewRequest(int departurePointId, int destinationPointId, double cargoWeight) throws DAOException;

    void deleteRequest(int requestId) throws DAOException;
}
