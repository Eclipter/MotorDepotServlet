package action;

import dao.TripDAO;
import entity.TripEntity;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;
import util.RequestParametersNames;

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
        TripDAO daoTrip = new TripDAO();
        Integer chosenRequestId = Integer.valueOf(req.getParameter(RequestParametersNames.CHOSEN_REQUEST));
        Integer chosenDriverId = Integer.valueOf(req.getParameter(RequestParametersNames.CHOSEN_DRIVER));
        try {
            logger.info("setting driver " + chosenDriverId + " for request " + chosenRequestId);
            daoTrip.setDriverOnTrip(chosenRequestId, chosenDriverId);
        } catch (DAOException e) {
            logger.error("error during setting driver on trip", e);
            req.setAttribute(RequestParametersNames.ERROR_MESSAGE, e.getMessage());
            return ConfigurationManager.getProperty(PageNamesConstants.ERROR);
        }

        logger.info("requesting all trips");
        List<TripEntity> allTrips = daoTrip.getAllTrips();
        //TODO: fix trip list request
        //TODO: fix reloading applications list
        //TODO: rewrite user tag
        //TODO: make ActionException and catch it in servlet
        //TODO: make drivers see only applications that are not binded to any drivers
        //TODO: make a message when admin is not online
        //TODO: check request params for null
        //TODO: check for all exceptions
        //TODO: move bundle names to a class
        req.setAttribute(RequestParametersNames.TRIPS, allTrips);
        return ConfigurationManager.getProperty(PageNamesConstants.TRIP_LIST);
    }
}
