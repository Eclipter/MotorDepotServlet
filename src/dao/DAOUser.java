package dao;

import entity.UserEntity;
import exception.DAOException;
import exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

import static dao.DAOTruck.logger;

/**
 * Created by USER on 21.04.2016.
 */
public class DAOUser extends DAOMotorDepot {

    public boolean isLoginOccupied(String login) {
        TypedQuery<UserEntity> query = getManager().createNamedQuery("UserEntity.searchByLogin", UserEntity.class);
        query.setParameter("login", login);
        List<UserEntity> resultList = query.getResultList();
        return !resultList.isEmpty();
    }

    public List<UserEntity> authenticateUser(String login, String pass) throws DAOException {
        TypedQuery<UserEntity> query = getManager().createNamedQuery("UserEntity.search", UserEntity.class);
        query.setParameter("login", login);
        query.setParameter("pass", pass);
        List<UserEntity> resultList = query.getResultList();
        return resultList;
    }

    public UserEntity registerNewUser(String login, String password) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        UserEntity userEntity = new UserEntity();
        try {
            transaction.begin();

            logger.info("Executing INSERT statement.");
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
