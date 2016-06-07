package action;

import dao.DAOTrip;
import entity.TripEntity;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Set driver on a trip.
 * Created by USER on 26.04.2016.
 */
public class SetDriverOnTripAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        DAOTrip daoTrip = new DAOTrip();
        Integer chosenApplicationId = Integer.valueOf(req.getParameter("chosenApplication"));
        Integer chosenDriverId = Integer.valueOf(req.getParameter("chosenDriver"));
        try {
            logger.info("setting driver " + chosenDriverId + " for application " + chosenApplicationId);
            daoTrip.setDriverOnTrip(chosenApplicationId, chosenDriverId);
        } catch (DAOException e) {
            logger.error("error during setting driver on trip", e);
            req.setAttribute("errorMessage", e.getMessage());
            return ConfigurationManager.getProperty("error");
        }

        logger.info("requesting all trips");
        List<TripEntity> allTrips = daoTrip.getAllTrips();
        req.setAttribute("trips", allTrips);
        return ConfigurationManager.getProperty("trip_list");
    }
}
