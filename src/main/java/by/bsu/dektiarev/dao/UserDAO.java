package by.bsu.dektiarev.dao;

import by.bsu.dektiarev.entity.User;
import by.bsu.dektiarev.exception.DAOException;

/**
 * DAO interface used to operate on USER table
 * Created by USER on 15.06.2016.
 */
public interface UserDAO extends GenericDAO {

    /**
     * Checks if a given login is already occupied
     *
     * @param login login to check
     * @throws DAOException
     */
    boolean isLoginOccupied(String login) throws DAOException;

    /**
     * Checks if the given combination of login and password corresponds to any driver
     *
     * @param login login to check
     * @param pass  password to check
     * @return list where user by.bsu.dektiarev.entity is kept or empty list if there is no such user
     * @throws DAOException
     */
    User authenticateUser(String login, String pass) throws DAOException;
}
