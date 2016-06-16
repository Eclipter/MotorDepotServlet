package dao;

import entity.User;
import exception.DAOException;

/**
 * Created by USER on 15.06.2016.
 */
public interface UserDAO extends GenericDAO {

    boolean isLoginOccupied(String login) throws DAOException;
    User authenticateUser(String login, String pass) throws DAOException;
    User addNewUser(String login, String password) throws DAOException;
}
