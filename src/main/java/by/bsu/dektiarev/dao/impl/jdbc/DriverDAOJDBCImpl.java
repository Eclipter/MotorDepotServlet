package by.bsu.dektiarev.dao.impl.jdbc;

import by.bsu.dektiarev.dao.DriverDAO;
import by.bsu.dektiarev.dao.util.ColumnName;
import by.bsu.dektiarev.dao.util.DatabaseQuery;
import by.bsu.dektiarev.dao.util.pool.ConnectionPool;
import by.bsu.dektiarev.entity.Driver;
import by.bsu.dektiarev.entity.*;
import by.bsu.dektiarev.entity.util.TruckState;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.DatabaseConnectionException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 15.06.2016.
 */
public class DriverDAOJDBCImpl implements DriverDAO {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public List<Driver> getDrivers(int offset, int limit) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_ALL_DRIVERS_LIMITED)) {
                statement.setInt(1, offset);
                statement.setInt(2, limit);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getDriversFromResultSet(resultSet);
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
    public List<Driver> getAllDriversWithHealthyTrucks() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_DRIVERS_WITH_HEALTHY_TRUCKS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getDriversFromResultSet(resultSet);
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
    public Integer getNumberOfDrivers() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_NUMBER_OF_DRIVERS)) {
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
    public Driver find(User user) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_DRIVER_BY_USER)) {
                statement.setInt(1, user.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Driver> driverList = getDriversFromResultSet(resultSet);
                    if (driverList.isEmpty()) {
                        return null;
                    } else {
                        return driverList.get(0);
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
    public Driver find(Request request) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_DRIVER_BY_REQUEST)) {
                statement.setInt(1, request.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Driver> driverList = getDriversFromResultSet(resultSet);
                    if (driverList.isEmpty()) {
                        return null;
                    } else {
                        return driverList.get(0);
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
    public void addDriver(String login, String password, Truck truck) throws DAOException {
        if(login == null || password == null || "".equals(login) || "".equals(password)) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            connection.setAutoCommit(false);
            Integer userId;
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.INSERT_USER,
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, login);
                statement.setString(2, password);
                statement.executeUpdate();
                try (ResultSet keySet = statement.getGeneratedKeys()) {
                    if(!keySet.next()) {
                        throw new DAOException(ExceptionalMessageKey.DML_EXCEPTION);
                    }
                    userId = keySet.getInt(1);
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.INSERT_DRIVER)) {
                statement.setInt(1, userId);
                statement.setInt(2, truck.getId());
                statement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error(e);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    private List<Driver> getDriversFromResultSet(ResultSet resultSet) throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        while (resultSet.next()) {
            Integer userId = resultSet.getInt(ColumnName.USER_ID);
            String login = resultSet.getString(ColumnName.LOGIN);
            String password = resultSet.getString(ColumnName.PASSWORD);
            Integer truckId = resultSet.getInt(ColumnName.TRUCK_ID);
            Double capacity = resultSet.getDouble(ColumnName.CAPACITY);
            Integer stateId = resultSet.getInt(ColumnName.STATE_ID);
            String stateName = resultSet.getString(ColumnName.STATE_NAME);
            Driver driver = new Driver();
            Truck truck = new Truck();
            TruckStateDTO truckState = new TruckStateDTO();
            driver.setId(userId);
            driver.setLogin(login);
            driver.setPassword(password);
            truck.setId(truckId);
            truck.setCapacity(capacity);
            truckState.setId(stateId);
            truckState.setTruckStateName(TruckState.valueOf(stateName));
            truck.setState(truckState);
            driver.setTruck(truck);
            drivers.add(driver);
        }
        return drivers;
    }
}
