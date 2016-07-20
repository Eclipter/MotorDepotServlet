package by.bsu.dektiarev.dao.impl.jdbc;

import by.bsu.dektiarev.dao.RequestDAO;
import by.bsu.dektiarev.dao.util.ColumnName;
import by.bsu.dektiarev.dao.util.DatabaseQuery;
import by.bsu.dektiarev.dao.util.pool.ConnectionPool;
import by.bsu.dektiarev.entity.Request;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.DatabaseConnectionException;
import by.bsu.dektiarev.exception.ExceptionalMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 15.06.2016.
 */
public class RequestDAOJDBCImpl implements RequestDAO {

    @Override
    public List<Request> getAllRequests() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_ALL_REQUESTS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getRequestsFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Request> getUnassignedRequests() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_UNASSIGNED_REQUESTS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getRequestsFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void addNewRequest(int cargoWeight) throws DAOException {
        if(cargoWeight < 0) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.INSERT_REQUEST)) {
                statement.setInt(1, cargoWeight);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteRequest(int requestId) throws DAOException {
        if(requestId <= 0) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.DELETE_REQUEST)) {
                statement.setInt(1, requestId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    private List<Request> getRequestsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Request> requestList = new ArrayList<>();
        while(resultSet.next()) {
            Integer requestId = resultSet.getInt(ColumnName.ID);
            Integer cargoWeight = resultSet.getInt(ColumnName.CARGO_WEIGHT);
            Request request = new Request();
            request.setId(requestId);
            request.setCargoWeight(cargoWeight);
            requestList.add(request);
        }
        return requestList;
    }
}
