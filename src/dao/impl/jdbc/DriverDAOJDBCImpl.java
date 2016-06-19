package dao.impl.jdbc;

import dao.DriverDAO;
import dao.util.ColumnName;
import dao.util.DatabaseQuery;
import dao.util.pool.ConnectionPool;
import entity.*;
import entity.util.TruckState;
import exception.DAOException;
import exception.DatabaseConnectionException;
import exception.ExceptionalMessage;
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
public class DriverDAOJDBCImpl implements DriverDAO {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public List<Driver> getAllDrivers() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_ALL_DRIVERS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getDriversFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Driver> getDriversWithHealthyTrucks() throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_DRIVERS_WITH_HEALTHY_TRUCKS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return getDriversFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Driver searchByUser(User user) throws DAOException {
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
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Driver searchByRequest(Request request) throws DAOException {
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
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void registerNewDriver(User user, Truck truck) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.INSERT_DRIVER)) {
                statement.setInt(1, user.getId());
                statement.setInt(2, truck.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
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
            Integer capacity = resultSet.getInt(ColumnName.CAPACITY);
            Integer stateId = resultSet.getInt(ColumnName.STATE_ID);
            String stateName = resultSet.getString(ColumnName.STATE_NAME);
            Driver driver = new Driver();
            User user = new User();
            Truck truck = new Truck();
            TruckStateDTO truckState = new TruckStateDTO();
            user.setId(userId);
            user.setLogin(login);
            user.setPassword(password);
            driver.setUserId(userId);
            driver.setUser(user);
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
