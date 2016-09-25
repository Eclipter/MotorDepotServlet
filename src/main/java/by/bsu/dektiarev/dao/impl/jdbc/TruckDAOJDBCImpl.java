package by.bsu.dektiarev.dao.impl.jdbc;

import by.bsu.dektiarev.dao.TruckDAO;
import by.bsu.dektiarev.dao.util.ColumnName;
import by.bsu.dektiarev.dao.util.DatabaseQuery;
import by.bsu.dektiarev.dao.util.pool.ConnectionPool;
import by.bsu.dektiarev.entity.Truck;
import by.bsu.dektiarev.entity.TruckStateDTO;
import by.bsu.dektiarev.entity.util.TruckState;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.DatabaseConnectionException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 15.06.2016.
 */
public class TruckDAOJDBCImpl implements TruckDAO {

    @Override
    public List<Truck> getTrucks(int offset) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_ALL_TRUCKS_LIMITED)) {
                statement.setInt(1, offset);
                statement.setInt(2, COLLECTION_FETCH_LIMIT);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getTrucksFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Truck getTruckByDriver(int driverId) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_TRUCK_BY_DRIVER_ID)) {
                statement.setLong(1, driverId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Truck> truckList = getTrucksFromResultSet(resultSet);
                    if(truckList.size() == 0) {
                        throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
                    }
                    return truckList.get(0);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Integer getNumberOfTrucks() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_NUMBER_OF_TRUCKS)) {
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
    public void changeTruckState(int truckId, TruckState truckStateToSet) throws DAOException {
        if(truckStateToSet == null) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            TruckState truckState;
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_TRUCK_BY_ID)) {
                statement.setInt(1, truckId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        truckState = TruckState.valueOf(resultSet.getString(ColumnName.STATE_NAME));
                    } else {
                        throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
                    }
                }
            }
            if(truckState.equals(truckStateToSet)) {
                throw new DAOException(ExceptionalMessageKey.TRUCK_HAS_THE_SAME_STATE);
            }
            try(PreparedStatement statement = connection.prepareStatement(DatabaseQuery.CHANGE_TRUCK_STATE)) {
                statement.setInt(1, truckStateToSet.ordinal() + 1);
                statement.setInt(2, truckId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Truck addTruck(String number, double capacity) throws DAOException {
        if(capacity < 0 || number == null || number.equals("")) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            Integer truckId;
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.INSERT_TRUCK,
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, number);
                statement.setDouble(2, capacity);
                statement.executeUpdate();
                try (ResultSet keySet = statement.getGeneratedKeys()) {
                    if(!keySet.next()) {
                        throw new DAOException(ExceptionalMessageKey.DML_EXCEPTION);
                    }
                    truckId = keySet.getInt(1);
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_TRUCK_BY_ID)) {
                statement.setInt(1, truckId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Truck> truckList = getTrucksFromResultSet(resultSet);
                    if(truckList.isEmpty()) {
                        throw new DAOException(ExceptionalMessageKey.SQL_ERROR);
                    }
                    else {
                       return truckList.get(0);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    private List<Truck> getTrucksFromResultSet(ResultSet resultSet) throws SQLException {
        List<Truck> truckList = new ArrayList<>();
        while(resultSet.next()) {
            Integer truckId = resultSet.getInt(ColumnName.ID);
            Double capacity = resultSet.getDouble(ColumnName.CAPACITY);
            String number = resultSet.getString(ColumnName.NUMBER);
            Integer stateId = resultSet.getInt(ColumnName.STATE_ID);
            String stateName = resultSet.getString(ColumnName.STATE_NAME);
            Truck truck = new Truck();
            truck.setId(truckId);
            truck.setNumber(number);
            truck.setCapacity(capacity);
            TruckStateDTO truckStateDTO = new TruckStateDTO();
            truckStateDTO.setId(stateId);
            truckStateDTO.setTruckStateName(TruckState.valueOf(stateName));
            truck.setState(truckStateDTO);
            truckList.add(truck);
        }
        return truckList;
    }
}
