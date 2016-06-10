package action;

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
import util.BundleName;
import util.InternationalizedBundleManager;
import util.RequestParameterName;
import util.URLConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action, responsible for signup process
 * Created by USER on 15.05.2016.
 */
public class SignupAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            String username = req.getParameter(RequestParameterName.USERNAME);
            String password = req.getParameter(RequestParameterName.PASSWORD);
            Integer truckCapacity = Integer.valueOf(req.getParameter(RequestParameterName.TRUCK_CAPACITY));
            if(username == null || password == null || truckCapacity == null) {
                throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                        ExceptionalMessage.MISSING_REQUEST_PARAMETERS,
                        (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
            }
            TruckDAO daoTruck = new TruckDAO();
            DriverDAO driverDAO = new DriverDAO();
            UserDAO daoUser = new UserDAO();
            logger.info("checking new user");
            if(daoUser.isLoginOccupied(username)) {
                logger.info("login " + username + " is already occupied");
                req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE, ExceptionalMessage.LOGIN_OCCUPIED);
                return URLConstant.GET_SIGNUP_FORM;
            }

            logger.info("registering new user");
            UserEntity userEntity = daoUser.addNewUser(username, password);
            TruckEntity truckEntity = daoTruck.addNewTruck(truckCapacity);
            driverDAO.registerNewDriver(userEntity, truckEntity);
            return URLConstant.GET_LOGIN_FORM;
        } catch (DAOException e) {
            throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                    e.getMessage(),
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
        }
    }
}
