package action;

import dao.DAODriver;
import dao.DAOTruck;
import dao.DAOUser;
import entity.TruckEntity;
import entity.UserEntity;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;

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

        DAOTruck daoTruck = new DAOTruck();
        DAODriver daoDriver = new DAODriver();
        DAOUser daoUser = new DAOUser();

        try {
            logger.info("checking new user");
            if(daoUser.isLoginOccupied(username)) {
                logger.info("login " + username + " is already occupied");
                req.getSession().setAttribute("errorMessage", ExceptionalMessage.LOGIN_OCCUPIED);
                return ConfigurationManager.getProperty("signup_form");
            }

            logger.info("registering new user");
            UserEntity userEntity = daoUser.registerNewUser(username, password);
            TruckEntity truckEntity = daoTruck.addNewTruck(truckCapacity);
            daoDriver.registerNewDriver(userEntity, truckEntity);
        } catch (DAOException e) {
            logger.error("error during registering new user", e);
            req.setAttribute("errorMessage", e.getMessage());
            return ConfigurationManager.getProperty("error");
        }

        return ConfigurationManager.getProperty("login");
    }
}
