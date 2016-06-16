package action;

import bean.UserInfoBean;
import dao.DriverDAO;
import dao.UserDAO;
import dao.util.DAOFactory;
import dao.util.DAOType;
import entity.Driver;
import entity.User;
import exception.ActionExecutionException;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.RequestParameterName;
import util.URLConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action, responsible for authentication process
 * Created by USER on 25.04.2016.
 */
public class LoginAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            UserDAO userDAO = (UserDAO) DAOFactory.getDAOFromFactory(DAOType.USER);
            String userName = req.getParameter(RequestParameterName.USERNAME);
            String password = req.getParameter(RequestParameterName.PASSWORD);
            if (userName == null || password == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            logger.info("authenticating user: " + userName + " " + password);
            User user = userDAO.authenticateUser(userName, password);
            if (!(user == null)) {
                logger.info("user found");
                HttpSession session = req.getSession();
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setUser(user);
                DriverDAO driverDAO = (DriverDAO) DAOFactory.getDAOFromFactory(DAOType.DRIVER);
                Driver driver = driverDAO.searchByUser(user);
                userInfoBean.setAdmin(driver == null);
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
