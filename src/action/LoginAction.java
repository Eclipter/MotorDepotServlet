package action;

import bean.UserInfoBean;
import dao.UserDAO;
import entity.UserEntity;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Login process
 * Created by USER on 25.04.2016.
 */
public class LoginAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            UserDAO daoUser = new UserDAO();
            String userName = req.getParameter("username");
            String password = req.getParameter("password");
            logger.info("authenticating user: " + userName + " " + password);
            List<UserEntity> userEntityList = daoUser.authenticateUser(userName, password);
            if (!userEntityList.isEmpty()) {
                logger.info("user found");
                UserEntity userEntity = userEntityList.get(0);
                HttpSession session = req.getSession();
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setUserEntity(userEntity);
                session.setAttribute("user", userInfoBean);
                return ConfigurationManager.getProperty(PageNamesConstants.INDEX);
            } else {
                throw new DAOException(ExceptionalMessage.WRONG_LOGIN_PASS);
            }
        }
        catch (DAOException e) {
            logger.error("error durng authenticating", e);
            req.setAttribute("errorMessage", e.getMessage());
            return ConfigurationManager.getProperty(PageNamesConstants.ERROR);
        }
    }
}
