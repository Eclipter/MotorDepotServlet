package action;

import action.bean.ActionResponse;
import action.bean.ActionType;
import bean.UserInfoBean;
import dao.UserDAO;
import entity.UserEntity;
import exception.ActionExecutionException;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.RequestParametersNames;
import util.URLConstants;

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
    public ActionResponse execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            UserDAO daoUser = new UserDAO();
            String userName = req.getParameter(RequestParametersNames.USERNAME);
            String password = req.getParameter(RequestParametersNames.PASSWORD);
            if(userName == null || password == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            logger.info("authenticating user: " + userName + " " + password);
            List<UserEntity> userEntityList = daoUser.authenticateUser(userName, password);
            if (!userEntityList.isEmpty()) {
                logger.info("user found");
                UserEntity userEntity = userEntityList.get(0);
                HttpSession session = req.getSession();
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setUserEntity(userEntity);
                session.setAttribute(RequestParametersNames.USER, userInfoBean);
                return new ActionResponse(URLConstants.GET_INDEX_PAGE, ActionType.REDIRECT);
            } else {
                throw new DAOException(ExceptionalMessage.WRONG_LOGIN_PASS);
            }
        }
        catch (DAOException e) {
            throw new ActionExecutionException("error during authenticating", e);
        }
    }
}
