package dao.impl.jdbc;

import dao.TruckDAO;
import dao.util.ColumnName;
import dao.util.DatabaseQuery;
import dao.util.connection.ConnectionPool;
import entity.Truck;
import entity.TruckStateDTO;
import entity.util.TruckState;
import exception.DAOException;
import exception.DatabaseConnectionException;
import exception.ExceptionalMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 15.06.2016.
 */
public class TruckDAOJDBCImpl implements TruckDAO {

    @Override
    public List<Truck> getAllTrucks() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_ALL_TRUCKS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getTrucksFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void changeTruckState(int truckId, TruckState truckStateToSet) throws DAOException {
        if(truckStateToSet == null) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            TruckState truckState;
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_TRUCK_BY_ID)) {
                statement.setInt(1, truckId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        truckState = TruckState.valueOf(resultSet.getString(ColumnName.STATE_NAME));
                    } else {
                        throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
                    }
                }
            }
            if(truckState.equals(truckStateToSet)) {
                throw new DAOException(ExceptionalMessage.TRUCK_HAS_THE_SAME_STATE);
            }
            try(PreparedStatement statement = connection.prepareStatement(DatabaseQuery.CHANGE_TRUCK_STATE)) {
                statement.setInt(1, truckStateToSet.ordinal() + 1);
                statement.setInt(2, truckId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Truck addNewTruck(int capacity) throws DAOException {
        if(capacity < 0) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            Integer truckId;
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.INSERT_TRUCK,
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, capacity);
                statement.executeUpdate();
                try (ResultSet keySet = statement.getGeneratedKeys()) {
                    if(!keySet.next()) {
                        throw new DAOException(ExceptionalMessage.DML_EXCEPTION);
                    }
                    truckId = keySet.getInt(1);
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_TRUCK_BY_ID)) {
                statement.setInt(1, truckId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Truck> truckList = getTrucksFromResultSet(resultSet);
                    if(truckList.isEmpty()) {
                        throw new DAOException(ExceptionalMessage.SQL_ERROR);
                    }
                    else {
                       return truckList.get(0);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    private List<Truck> getTrucksFromResultSet(ResultSet resultSet) throws SQLException {
        List<Truck> truckList = new ArrayList<>();
        while(resultSet.next()) {
            Integer truckId = resultSet.getInt(ColumnName.ID);
            Integer capacity = resultSet.getInt(ColumnName.CAPACITY);
            Integer stateId = resultSet.getInt(ColumnName.STATE_ID);
            String stateName = resultSet.getString(ColumnName.STATE_NAME);
            Truck truck = new Truck();
            truck.setId(truckId);
            truck.setCapacity(capacity);
            TruckStateDTO truckStateDTO = new TruckStateDTO();
            truckStateDTO.setId(stateId);
            truckStateDTO.setTruckStateName(TruckState.valueOf(stateName));
            truck.setStateByStateId(truckStateDTO);
            truckList.add(truck);
        }
        return truckList;
    }
}
