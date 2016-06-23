package by.bsu.dektiarev.action;

import by.bsu.dektiarev.bean.UserInfoBean;
import by.bsu.dektiarev.dao.DriverDAO;
import by.bsu.dektiarev.dao.UserDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.Driver;
import by.bsu.dektiarev.entity.User;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.RequestParameterName;
import by.bsu.dektiarev.util.URLConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action, responsible for authentication process
 * Created by USER on 25.04.2016.
 */public class LoginAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            UserDAO userDAO = (UserDAO) DAOFactory.getDAOFromFactory(DAOType.USER);
            String userName = req.getParameter(RequestParameterName.USERNAME);
            String password = req.getParameter(RequestParameterName.PASSWORD);
            if (userName == null || password == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            LOG.info("authenticating user: " + userName + " " + password);
            User user = userDAO.authenticateUser(userName, password);
            if (user != null) {
                LOG.info("user found");
                HttpSession session = req.getSession();
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setUser(user);
                DriverDAO driverDAO = (DriverDAO) DAOFactory.getDAOFromFactory(DAOType.DRIVER);
                Driver driver = driverDAO.searchByUser(user);
                if(driver == null) {
                    userInfoBean.setAdmin(true);
                }
                session.setAttribute(RequestParameterName.USER, userInfoBean);
                return URLConstant.GET_INDEX_PAGE;
            } else {
                throw new ActionExecutionException(ExceptionalMessage.WRONG_LOGIN_PASS);
            }
        } catch (DAOException ex) {
            throw new ActionExecutionException(ex.getMessage());
        }
    }
}
