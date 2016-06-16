package dao.impl.jdbc;

import dao.UserDAO;
import dao.util.ColumnName;
import dao.util.DatabaseQuery;
import dao.util.connection.ConnectionPool;
import entity.User;
import exception.DAOException;
import exception.DatabaseConnectionException;
import exception.ExceptionalMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 15.06.2016.
 */
public class UserDAOJDBCImpl implements UserDAO {

    @Override
    public boolean isLoginOccupied(String login) throws DAOException {
        if(login == null || "".equals(login)) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_USER_BY_LOGIN)) {
                statement.setString(1, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public User authenticateUser(String login, String pass) throws DAOException {
        if(login == null || pass == null || "".equals(login) || "".equals(pass)) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_USER_BY_LOGIN_AND_PASSWORD)) {
                statement.setString(1, login);
                statement.setString(2, pass);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<User> userList = getUserFromResultSet(resultSet);
                    if(userList.isEmpty()) {
                        return null;
                    }
                    else {
                        return userList.get(0);
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
    public User addNewUser(String login, String password) throws DAOException {
        if(login == null || password == null || "".equals(login) || "".equals(password)) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            Integer userId;
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.INSERT_USER,
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, login);
                statement.setString(2, password);
                statement.executeUpdate();
                try (ResultSet keySet = statement.getGeneratedKeys()) {
                    if(!keySet.next()) {
                        throw new DAOException(ExceptionalMessage.DML_EXCEPTION);
                    }
                    userId = keySet.getInt(1);
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_USER_BY_ID)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<User> userList = getUserFromResultSet(resultSet);
                    if(userList.isEmpty()) {
                        throw new DAOException(ExceptionalMessage.SQL_ERROR);
                    }
                    else {
                        return userList.get(0);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessage.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    private List<User> getUserFromResultSet(ResultSet resultSet) throws SQLException {
        List<User> userList = new ArrayList<>();
        while(resultSet.next()) {
            Integer userId = resultSet.getInt(ColumnName.ID);
            String login = resultSet.getString(ColumnName.LOGIN);
            String password = resultSet.getString(ColumnName.PASSWORD);
            User user = new User();
            user.setId(userId);
            user.setLogin(login);
            user.setPassword(password);
            userList.add(user);
        }
        return userList;
    }
}
