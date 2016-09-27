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
     * Retrieves all the requests, no more than the limit
     *
     * @param offset from which record to start
     * @return request list
     * @throws DAOException
     */
    List<Request> getRequests(int offset, int limit) throws DAOException;

    /**
     * Retrieves all unassigned requests
     *
     * @return request list
     * @throws DAOException
     */
    List<Request> getUnassignedRequests() throws DAOException;

    /**
     * Retrieves unassigned requests, no more than the limit
     *
     * @param offset from which record to start
     * @return request list
     * @throws DAOException
     */
    List<Request> getUnassignedRequests(int offset, int limit) throws DAOException;

    /**
     * Retrieves the whole number of requests
     *
     * @return number of requests
     * @throws DAOException
     */
    Integer getNumberOfAllRequests() throws DAOException;

    /**
     * Retrieves the whole number of unassigned requests
     *
     * @return number of requests
     * @throws DAOException
     */
    Integer getNumberOfUnassignedRequests() throws DAOException;

    /**
     * Adds new request to the database
     *
     * @param departurePointId
     * @param destinationPointId
     * @param cargoWeight        cargo weight parameter
     * @throws DAOException in case of DML error
     */
    void addRequest(int departurePointId, int destinationPointId, double cargoWeight) throws DAOException;

    /**
     * Removes the specified request
     *
     * @param requestId id of request that should be removed
     * @throws DAOException
     */
    void deleteRequest(int requestId) throws DAOException;
}
