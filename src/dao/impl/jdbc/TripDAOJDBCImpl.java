package dao.impl.jdbc;

import dao.TripDAO;
import dao.util.ColumnName;
import dao.util.DatabaseQuery;
import dao.util.pool.ConnectionPool;
import entity.*;
import entity.util.TruckState;
import exception.DAOException;
import exception.DatabaseConnectionException;
import exception.ExceptionalMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 15.06.2016.
 */
public class TripDAOJDBCImpl implements TripDAO {

    @Override
    public List<Trip> getAllTrips() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_ALL_TRIPS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getTripsFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Trip> getTripsByDriver(int driverId) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_TRIPS_BY_DRIVER)) {
                statement.setInt(1, driverId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getTripsFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void assignDriverToATrip(int requestId, int driverId) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            Integer capacity;
            Integer cargoWeight;
            TruckState truckState;
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_REQUEST_BY_ID)) {
                statement.setInt(1, requestId);
                try(ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        cargoWeight = resultSet.getInt(ColumnName.CARGO_WEIGHT);
                    }
                    else {
                        throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
                    }
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_TRUCK_BY_DRIVER_ID)) {
                statement.setInt(1, driverId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        capacity = resultSet.getInt(ColumnName.CAPACITY);
                        truckState = TruckState.valueOf(resultSet.getString(ColumnName.STATE_NAME));
                    }
                    else {
                        throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
                    }
                }
            }
            if(cargoWeight > capacity) {
                throw new DAOException(ExceptionalMessage.WEIGHT_MORE_THAN_CAPACITY);
            }
            if(!TruckState.OK.equals(truckState)) {
                throw new DAOException(ExceptionalMessage.TRUCK_NOT_OK);
            }
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.INSERT_TRIP)) {
                statement.setInt(1, requestId);
                statement.setInt(2, driverId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void changeTripState(Integer tripId, boolean state) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            Boolean tripState;
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_TRIP_BY_ID)) {
                statement.setInt(1, tripId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Trip> tripList = getTripsFromResultSet(resultSet);
                    if(tripList.isEmpty()) {
                        throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
                    } else {
                        tripState = tripList.get(0).getIsComplete();
                    }
                }
            }
            if(tripState.equals(state)) {
                throw new DAOException(ExceptionalMessage.TRIP_HAS_THIS_STATE);
            }
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.CHANGE_TRIP_STATE)) {
                statement.setBoolean(1, state);
                statement.setInt(2, tripId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    private List<Trip> getTripsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Trip> tripList = new ArrayList<>();
        while(resultSet.next()) {
            Integer tripId = resultSet.getInt(ColumnName.ID);
            Integer requestId = resultSet.getInt(ColumnName.REQUEST_ID);
            Integer cargoWeight = resultSet.getInt(ColumnName.CARGO_WEIGHT);
            Integer userId = resultSet.getInt(ColumnName.USER_ID);
            String login = resultSet.getString(ColumnName.LOGIN);
            String password = resultSet.getString(ColumnName.PASSWORD);
            Integer truckId = resultSet.getInt(ColumnName.TRUCK_ID);
            Integer capacity = resultSet.getInt(ColumnName.CAPACITY);
            Integer stateId = resultSet.getInt(ColumnName.STATE_ID);
            String stateName = resultSet.getString(ColumnName.STATE_NAME);
            Boolean isComplete = resultSet.getBoolean(ColumnName.IS_COMPLETE);
            Trip trip = new Trip();
            trip.setId(tripId);
            trip.setIsComplete(isComplete);
            Driver driver = new Driver();
            driver.setUserId(userId);
            User user = new User();
            user.setId(userId);
            user.setLogin(login);
            user.setPassword(password);
            driver.setUser(user);
            Truck truck = new Truck();
            truck.setId(truckId);
            truck.setCapacity(capacity);
            TruckStateDTO truckState = new TruckStateDTO();
            truckState.setId(stateId);
            truckState.setTruckStateName(TruckState.valueOf(stateName));
            truck.setState(truckState);
            driver.setTruck(truck);
            trip.setDriver(driver);
            Request request = new Request();
            request.setId(requestId);
            request.setCargoWeight(cargoWeight);
            trip.setRequest(request);
            tripList.add(trip);
        }
        return tripList;
    }
}
