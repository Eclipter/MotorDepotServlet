package dao;

import entity.UserEntity;
import exception.DAOException;
import exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO class used to operate on USER table
 * Created by USER on 21.04.2016.
 */
public class UserDAO extends GenericDAO {

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "pass";
    private static final String SEARCH_BY_LOGIN_QUERY = "UserEntity.searchByLogin";
    private static final String SEARCH_QUERY = "UserEntity.search";

    /**
     * Checks if a given login is already occupied
     * @param login login to check
     */
    public boolean isLoginOccupied(String login) {
        TypedQuery<UserEntity> query = getManager().createNamedQuery(SEARCH_BY_LOGIN_QUERY, UserEntity.class);
        query.setParameter(LOGIN_PARAMETER, login);
        List<UserEntity> resultList = query.getResultList();
        return !resultList.isEmpty();
    }

    /**
     * Checks if the given combination of login and password corresponds to any driver
     * @param login login to check
     * @param pass password to check
     * @return list where user entity is kept or empty list if there is no such user
     */
    public List<UserEntity> authenticateUser(String login, String pass) {
        TypedQuery<UserEntity> query = getManager().createNamedQuery(SEARCH_QUERY, UserEntity.class);
        query.setParameter(LOGIN_PARAMETER, login);
        query.setParameter(PASSWORD_PARAMETER, pass);
        return query.getResultList();
    }

    /**
     * Adds new user to the database
     * @return user entity that is saved to the database
     * @throws DAOException in case of DML error
     */
    public UserEntity addNewUser(String login, String password) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        UserEntity userEntity = new UserEntity();
        try {
            transaction.begin();
            userEntity.setLogin(login);
            userEntity.setPassword(password);
            getManager().persist(userEntity);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.DML_EXCEPTION);
        } finally {
            getManager().clear();
        }
        return userEntity;
    }
}
