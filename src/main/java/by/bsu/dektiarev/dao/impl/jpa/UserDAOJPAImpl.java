package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.UserDAO;
import by.bsu.dektiarev.entity.User;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAOJPAImpl extends GenericDAOJPAImpl implements UserDAO {

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "pass";
    private static final String SEARCH_BY_LOGIN_QUERY = "User.searchByLogin";
    private static final String SEARCH_QUERY = "User.authenticate";

    private static final Logger LOG = LogManager.getLogger();

    public UserDAOJPAImpl(EntityManager manager) {
        super(manager);
    }

    @Override
    public boolean isLoginOccupied(String login) throws DAOException {
        if(login == null || "".equals(login)) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try {
            TypedQuery<User> query = getManager().createNamedQuery(SEARCH_BY_LOGIN_QUERY, User.class);
            query.setParameter(LOGIN_PARAMETER, login);
            List<User> resultList = query.getResultList();
            return !resultList.isEmpty();
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public User authenticateUser(String login, String pass) throws DAOException {
        if(login == null || pass == null || "".equals(login) || "".equals(pass)) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try {
            TypedQuery<User> query = getManager().createNamedQuery(SEARCH_QUERY, User.class);
            query.setParameter(LOGIN_PARAMETER, login);
            query.setParameter(PASSWORD_PARAMETER, pass);
            List<User> userList = query.getResultList();
            if(userList.isEmpty()) {
                return null;
            }
            else {
                return userList.get(0);
            }
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }
}
