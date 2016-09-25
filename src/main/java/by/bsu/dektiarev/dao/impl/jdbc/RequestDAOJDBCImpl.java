package by.bsu.dektiarev.dao.impl.jdbc;

import by.bsu.dektiarev.dao.RequestDAO;
import by.bsu.dektiarev.dao.util.ColumnName;
import by.bsu.dektiarev.dao.util.DatabaseQuery;
import by.bsu.dektiarev.dao.util.pool.ConnectionPool;
import by.bsu.dektiarev.entity.Request;
import by.bsu.dektiarev.entity.Station;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.DatabaseConnectionException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;

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
    public List<Request> getRequests(int offset) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_ALL_REQUESTS_LIMITED)) {
                statement.setInt(1, offset);
                statement.setInt(2, COLLECTION_FETCH_LIMIT);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getRequestsFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Request> getUnassignedRequests() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_ALL_UNASSIGNED_REQUESTS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getRequestsFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Request> getUnassignedRequests(int offset) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_ALL_UNASSIGNED_REQUESTS_LIMITED)) {
                statement.setInt(1, offset);
                statement.setInt(2, COLLECTION_FETCH_LIMIT);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getRequestsFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Integer getNumberOfAllRequests() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_NUMBER_OF_REQUESTS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        return new Long(resultSet.getLong(ColumnName.COUNT)).intValue();
                    } else {
                        throw new DAOException(ExceptionalMessageKey.DML_EXCEPTION);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Integer getNumberOfUnassignedRequests() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_NUMBER_OF_UNASSIGNED_REQUESTS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        return new Long(resultSet.getLong(ColumnName.COUNT)).intValue();
                    } else {
                        throw new DAOException(ExceptionalMessageKey.DML_EXCEPTION);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void addRequest(int departurePointId, int destinationPointId, double cargoWeight) throws DAOException {
        if(cargoWeight < 0) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.INSERT_REQUEST)) {
                statement.setInt(1, departurePointId);
                statement.setInt(2, destinationPointId);
                statement.setDouble(3, cargoWeight);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteRequest(int requestId) throws DAOException {
        if(requestId <= 0) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.DELETE_REQUEST)) {
                statement.setInt(1, requestId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    private List<Request> getRequestsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Request> requestList = new ArrayList<>();
        while(resultSet.next()) {
            Integer requestId = resultSet.getInt(ColumnName.ID);
            Integer departureStationId = resultSet.getInt(ColumnName.DEPARTURE_STATION_ID);
            Integer destinationStationId = resultSet.getInt(ColumnName.DESTINATION_STATION_ID);
            String departureName = resultSet.getString(ColumnName.DEPARTURE_NAME);
            String destinationName = resultSet.getString(ColumnName.DESTINATION_NAME);
            String departureAddress = resultSet.getString(ColumnName.DEPARTURE_ADDRESS);
            String destinationAddress = resultSet.getString(ColumnName.DESTINATION_ADDRESS);
            Station departureStation = new Station();
            Station destinationStation = new Station();
            departureStation.setId(departureStationId);
            departureStation.setName(departureName);
            departureStation.setAddress(departureAddress);
            destinationStation.setId(destinationStationId);
            destinationStation.setName(destinationName);
            destinationStation.setAddress(destinationAddress);
            Double cargoWeight = resultSet.getDouble(ColumnName.CARGO_WEIGHT);
            Request request = new Request();
            request.setId(requestId);
            request.setCargoWeight(cargoWeight);
            request.setDepartureStation(departureStation);
            request.setDestinationStation(destinationStation);
            requestList.add(request);
        }
        return requestList;
    }
}
