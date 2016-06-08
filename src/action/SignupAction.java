package action;

import dao.DriverDAO;
import dao.TruckDAO;
import dao.UserDAO;
import entity.TruckEntity;
import entity.UserEntity;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by USER on 15.05.2016.
 */
public class SignupAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        int truckCapacity = Integer.parseInt(req.getParameter("truckCapacity"));

        TruckDAO daoTruck = new TruckDAO();
        DriverDAO driverDAO = new DriverDAO();
        UserDAO daoUser = new UserDAO();

        try {
            logger.info("checking new user");
            if(daoUser.isLoginOccupied(username)) {
                logger.info("login " + username + " is already occupied");
                req.getSession().setAttribute("errorMessage", ExceptionalMessage.LOGIN_OCCUPIED);
                return ConfigurationManager.getProperty(PageNamesConstants.SIGNUP_FORM);
            }

            logger.info("registering new user");
            UserEntity userEntity = daoUser.registerNewUser(username, password);
            TruckEntity truckEntity = daoTruck.addNewTruck(truckCapacity);
            driverDAO.registerNewDriver(userEntity, truckEntity);
        } catch (DAOException e) {
            logger.error("error during registering new user", e);
            req.setAttribute("errorMessage", e.getMessage());
            return ConfigurationManager.getProperty(PageNamesConstants.ERROR);
        }

        return ConfigurationManager.getProperty(PageNamesConstants.LOGIN);
    }
}
