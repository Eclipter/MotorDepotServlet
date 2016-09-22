package by.bsu.dektiarev.dao.impl.jdbc;

import by.bsu.dektiarev.dao.UserDAO;
import by.bsu.dektiarev.dao.util.ColumnName;
import by.bsu.dektiarev.dao.util.DatabaseQuery;
import by.bsu.dektiarev.dao.util.pool.ConnectionPool;
import by.bsu.dektiarev.entity.User;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.DatabaseConnectionException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;

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
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try (Connection connection = ConnectionPool.getInstance().takeConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DatabaseQuery.GET_USER_BY_LOGIN)) {
                statement.setString(1, login);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
        } catch (DatabaseConnectionException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public User authenticateUser(String login, String pass) throws DAOException {
        if(login == null || pass == null || "".equals(login) || "".equals(pass)) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
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
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, e);
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
