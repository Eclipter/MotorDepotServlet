package dao;

import entity.UserEntity;
import exception.DAOException;
import exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by USER on 21.04.2016.
 */
public class UserDAO extends MotorDepotDAO {

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "pass";

    public boolean isLoginOccupied(String login) {
        TypedQuery<UserEntity> query = getManager().createNamedQuery("UserEntity.searchByLogin", UserEntity.class);
        query.setParameter(LOGIN_PARAMETER, login);
        List<UserEntity> resultList = query.getResultList();
        return !resultList.isEmpty();
    }

    public List<UserEntity> authenticateUser(String login, String pass) throws DAOException {
        TypedQuery<UserEntity> query = getManager().createNamedQuery("UserEntity.search", UserEntity.class);
        query.setParameter(LOGIN_PARAMETER, login);
        query.setParameter(PASSWORD_PARAMETER, pass);
        List<UserEntity> resultList = query.getResultList();
        return resultList;
    }

    public UserEntity registerNewUser(String login, String password) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        UserEntity userEntity = new UserEntity();
        try {
            transaction.begin();
            userEntity.setLogin(login);
            userEntity.setPassword(password);
            getManager().persist(userEntity);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.SQL_EXCEPTION);
        } finally {
            getManager().clear();
        }
        return userEntity;
    }
}
