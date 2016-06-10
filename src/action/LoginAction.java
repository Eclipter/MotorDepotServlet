package action;

import bean.UserInfoBean;
import dao.UserDAO;
import entity.UserEntity;
import exception.ActionExecutionException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.BundleName;
import util.InternationalizedBundleManager;
import util.RequestParameterName;
import util.URLConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Action, responsible for authentication process
 * Created by USER on 25.04.2016.
 */
public class LoginAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        UserDAO daoUser = new UserDAO();
        String userName = req.getParameter(RequestParameterName.USERNAME);
        String password = req.getParameter(RequestParameterName.PASSWORD);
        if (userName == null || password == null) {
            throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                    ExceptionalMessage.MISSING_REQUEST_PARAMETERS,
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
        }
        logger.info("authenticating user: " + userName + " " + password);
        List<UserEntity> userEntityList = daoUser.authenticateUser(userName, password);
        if (!userEntityList.isEmpty()) {
            logger.info("user found");
            UserEntity userEntity = userEntityList.get(0);
            HttpSession session = req.getSession();
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setUserEntity(userEntity);
            session.setAttribute(RequestParameterName.USER, userInfoBean);
            return URLConstant.GET_INDEX_PAGE;
        } else {
            throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                    ExceptionalMessage.WRONG_LOGIN_PASS,
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
        }
    }
}
