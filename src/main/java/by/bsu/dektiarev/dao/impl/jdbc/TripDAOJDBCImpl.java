package by.bsu.dektiarev.dao.impl.jdbc;

import by.bsu.dektiarev.dao.TripDAO;
import by.bsu.dektiarev.dao.util.ColumnName;
import by.bsu.dektiarev.dao.util.DatabaseQuery;
import by.bsu.dektiarev.dao.util.pool.ConnectionPool;
import by.bsu.dektiarev.entity.*;
import by.bsu.dektiarev.entity.util.TruckState;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.DatabaseConnectionException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public List<Trip> getTrips(int offset, int limit) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_ALL_TRIPS_LIMITED)) {
                statement.setInt(1, offset);
                statement.setInt(2, limit);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getTripsFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            LOG.error(e);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Trip> getTripsByDriver(int driverId, int offset, int limit) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_TRIPS_BY_DRIVER_LIMITED)) {
                statement.setInt(1, driverId);
                statement.setInt(2, offset);
                statement.setInt(3, limit);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getTripsFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            LOG.error(e);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Integer getNumberOfTrips() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_NUMBER_OF_TRIPS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        return new Long(resultSet.getLong(ColumnName.COUNT)).intValue();
                    } else {
                        throw new DAOException(ExceptionalMessageKey.DML_EXCEPTION);
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error(e);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Integer getNumberOfTripsByDriver(int driverId) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_NUMBER_OF_TRIPS_BY_DRIVER)) {
                statement.setInt(1, driverId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        return new Long(resultSet.getLong(ColumnName.COUNT)).intValue();
                    } else {
                        throw new DAOException(ExceptionalMessageKey.DML_EXCEPTION);
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error(e);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Integer getNumberOfCompletedTripsByDriver(int driverId) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    DatabaseQuery.GET_NUMBER_OF_COMPLETED_TRIPS_BY_DRIVER)) {
                statement.setInt(1, driverId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        return new Long(resultSet.getLong(ColumnName.COUNT)).intValue();
                    } else {
                        throw new DAOException(ExceptionalMessageKey.DML_EXCEPTION);
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error(e);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void addTrip(int requestId, int driverId) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            Double capacity;
            Double cargoWeight;
            TruckState truckState;
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_REQUEST_BY_ID)) {
                statement.setInt(1, requestId);
                try(ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        cargoWeight = resultSet.getDouble(ColumnName.CARGO_WEIGHT);
                    }
                    else {
                        throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
                    }
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_TRUCK_BY_DRIVER_ID)) {
                statement.setInt(1, driverId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        capacity = resultSet.getDouble(ColumnName.CAPACITY);
                        truckState = TruckState.valueOf(resultSet.getString(ColumnName.STATE_NAME));
                    }
                    else {
                        throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
                    }
                }
            }
            if(cargoWeight > capacity) {
                throw new DAOException(ExceptionalMessageKey.WEIGHT_MORE_THAN_CAPACITY);
            }
            if(!TruckState.OK.equals(truckState)) {
                throw new DAOException(ExceptionalMessageKey.TRUCK_NOT_OK);
            }
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.INSERT_TRIP)) {
                statement.setInt(1, requestId);
                statement.setInt(2, driverId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOG.error(e);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void changeTripState(int tripId, boolean state) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            Boolean tripState;
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_TRIP_BY_ID)) {
                statement.setInt(1, tripId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Trip> tripList = getTripsFromResultSet(resultSet);
                    if(tripList.isEmpty()) {
                        throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
                    } else {
                        tripState = tripList.get(0).getIsComplete();
                    }
                }
            }
            if(tripState.equals(state)) {
                throw new DAOException(ExceptionalMessageKey.TRIP_HAS_THIS_STATE);
            }
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.CHANGE_TRIP_STATE)) {
                statement.setBoolean(1, state);
                statement.setInt(2, tripId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOG.error(e);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    private List<Trip> getTripsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Trip> tripList = new ArrayList<>();
        while(resultSet.next()) {
            Integer tripId = resultSet.getInt(ColumnName.ID);
            Integer requestId = resultSet.getInt(ColumnName.REQUEST_ID);
            Double cargoWeight = resultSet.getDouble(ColumnName.CARGO_WEIGHT);
            Integer userId = resultSet.getInt(ColumnName.USER_ID);
            String login = resultSet.getString(ColumnName.LOGIN);
            String password = resultSet.getString(ColumnName.PASSWORD);
            Integer truckId = resultSet.getInt(ColumnName.TRUCK_ID);
            Double capacity = resultSet.getDouble(ColumnName.CAPACITY);
            Integer stateId = resultSet.getInt(ColumnName.STATE_ID);
            String stateName = resultSet.getString(ColumnName.STATE_NAME);
            Boolean isComplete = resultSet.getBoolean(ColumnName.IS_COMPLETE);
            Trip trip = new Trip();
            trip.setId(tripId);
            trip.setIsComplete(isComplete);
            Driver driver = new Driver();
            driver.setId(userId);
            driver.setLogin(login);
            driver.setPassword(password);
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
            request.setDepartureStation(departureStation);
            request.setDestinationStation(destinationStation);

            trip.setRequest(request);
            tripList.add(trip);
        }
        return tripList;
    }
}
