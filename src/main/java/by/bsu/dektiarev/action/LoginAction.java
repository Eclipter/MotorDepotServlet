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
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import by.bsu.dektiarev.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Action, responsible for authentication process
 * Created by USER on 25.04.2016.
 */
public class LoginAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            UserDAO userDAO = (UserDAO) daoFactory.getDAOFromFactory(DAOType.USER);
            String userName = req.getParameter(RequestParameterName.USERNAME);
            String password = req.getParameter(RequestParameterName.PASSWORD);
            if (userName == null || password == null) {
                throw new ActionExecutionException(ExceptionalMessageKey.MISSING_REQUEST_PARAMETERS);
            }
            LOG.info("authenticating user: " + userName + " " + password);
            User user = userDAO.authenticateUser(userName, password);
            if (user != null) {
                LOG.info("user found");
                HttpSession session = req.getSession();
                UserInfoBean userInfoBean = new UserInfoBean();
                DriverDAO driverDAO = (DriverDAO) daoFactory.getDAOFromFactory(DAOType.DRIVER);
                Driver driver = driverDAO.find(user);
                if(driver != null) {
                    userInfoBean.setUser(driver);
                    userInfoBean.setAdmin(false);
                }
                else {
                    userInfoBean.setUser(user);
                    userInfoBean.setAdmin(true);
                }
                session.setAttribute(RequestParameterName.USER, userInfoBean);
                return URLConstant.GET_MAIN_PAGE;
            } else {
                req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE,
                        InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                                ExceptionalMessageKey.WRONG_LOGIN_PASS,
                                (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
                return URLConstant.GET_LOGIN_FORM;
            }
        } catch (DAOException ex) {
            throw new ActionExecutionException(ex.getMessage());
        }
    }
}
