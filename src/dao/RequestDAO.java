package dao;

import entity.Request;
import exception.DAOException;

import java.util.List;

/**
 * Created by USER on 15.06.2016.
 */
public interface RequestDAO extends GenericDAO {

    List<Request> getAllRequests() throws DAOException;
    List<Request> getUnassignedRequests() throws DAOException;
    void addNewRequest(int cargoWeight) throws DAOException;
}
