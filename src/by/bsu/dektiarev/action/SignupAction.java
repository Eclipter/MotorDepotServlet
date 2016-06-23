package by.bsu.dektiarev.action;

import by.bsu.dektiarev.dao.DriverDAO;
import by.bsu.dektiarev.dao.TruckDAO;
import by.bsu.dektiarev.dao.UserDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.Truck;
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

/**
 * Action, responsible for signup process
 * Created by USER on 15.05.2016.
 */
public class SignupAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            String username = req.getParameter(RequestParameterName.USERNAME);
            String password = req.getParameter(RequestParameterName.PASSWORD);
            Integer truckCapacity = Integer.valueOf(req.getParameter(RequestParameterName.TRUCK_CAPACITY));
            if (username == null || password == null || truckCapacity == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            TruckDAO truckDAO = (TruckDAO) DAOFactory.getDAOFromFactory(DAOType.TRUCK);
            DriverDAO driverDAO = (DriverDAO) DAOFactory.getDAOFromFactory(DAOType.DRIVER);
            UserDAO userDAO = (UserDAO) DAOFactory.getDAOFromFactory(DAOType.USER);
            LOG.info("checking new user");
            if (userDAO.isLoginOccupied(username)) {
                LOG.info("login " + username + " is already occupied");
                req.getSession().setAttribute(RequestParameterName.ERROR_MESSAGE, ExceptionalMessage.LOGIN_OCCUPIED);
                return URLConstant.GET_SIGNUP_FORM;
            }

            LOG.info("registering new user");
            User user = userDAO.addNewUser(username, password);
            Truck truck = truckDAO.addNewTruck(truckCapacity);
            driverDAO.registerNewDriver(user, truck);
            return URLConstant.GET_LOGIN_FORM;
        } catch (DAOException e) {
            throw new ActionExecutionException(e.getMessage());
        }
    }
}
