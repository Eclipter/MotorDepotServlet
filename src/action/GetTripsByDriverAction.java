package action;

import action.controller.UserController;
import dao.DAODriver;
import dao.DAOTrip;
import entity.TripEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Get all trips of a particular driver.
 * Created by USER on 26.04.2016.
 */
public class GetTripsByDriverAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        DAOTrip daoTrip = new DAOTrip();
        DAODriver daoDriver = new DAODriver();
        UserController userController = (UserController) req.getSession().getAttribute("user");
        Integer driverId = userController.getUserEntity().getId();
        logger.info("requesting trips of driver " + driverId);
        List<TripEntity> allTrips = daoTrip.getTripsByDriver(driverId);
        req.setAttribute("trips", allTrips);
        return ConfigurationManager.getProperty("trip_list");
    }
}
