package action;

import action.bean.ActionResponse;
import action.bean.ActionType;
import dao.DriverDAO;
import dao.TruckDAO;
import dao.UserDAO;
import entity.TruckEntity;
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

/**
 * Created by USER on 15.05.2016.
 */
public class SignupAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public ActionResponse execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            String username = req.getParameter(RequestParametersNames.USERNAME);
            String password = req.getParameter(RequestParametersNames.PASSWORD);
            Integer truckCapacity = Integer.valueOf(req.getParameter(RequestParametersNames.TRUCK_CAPACITY));
            if(username == null || password == null || truckCapacity == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            TruckDAO daoTruck = new TruckDAO();
            DriverDAO driverDAO = new DriverDAO();
            UserDAO daoUser = new UserDAO();
            logger.info("checking new user");
            if(daoUser.isLoginOccupied(username)) {
                logger.info("login " + username + " is already occupied");
                req.getSession().setAttribute(RequestParametersNames.ERROR_MESSAGE, ExceptionalMessage.LOGIN_OCCUPIED);
                return new ActionResponse(URLConstants.GET_SIGNUP_FORM, ActionType.REDIRECT);
            }

            logger.info("registering new user");
            UserEntity userEntity = daoUser.registerNewUser(username, password);
            TruckEntity truckEntity = daoTruck.addNewTruck(truckCapacity);
            driverDAO.registerNewDriver(userEntity, truckEntity);
            return new ActionResponse(URLConstants.GET_LOGIN_FORM, ActionType.REDIRECT);
        } catch (DAOException e) {
            throw new ActionExecutionException("error during registering new user", e);
        }
    }
}
