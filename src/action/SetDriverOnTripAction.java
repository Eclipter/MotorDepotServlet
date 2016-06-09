package action;

import dao.TripDAO;
import entity.TripEntity;
import exception.ActionExecutionException;
import exception.DAOException;
import exception.ExceptionalMessage;
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
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            TripDAO daoTrip = new TripDAO();
            Integer chosenRequestId = Integer.valueOf(req.getParameter(RequestParametersNames.CHOSEN_REQUEST));
            Integer chosenDriverId = Integer.valueOf(req.getParameter(RequestParametersNames.CHOSEN_DRIVER));
            if(chosenDriverId == null || chosenRequestId == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            logger.info("checking parameters");
            List<TripEntity> tripsByDriverAndRequest = daoTrip.getTripByDriverAndRequest(chosenDriverId, chosenRequestId);
            if(!tripsByDriverAndRequest.isEmpty()) {
                throw new ActionExecutionException(ExceptionalMessage.TRIP_EXISTS);
            }
            logger.info("setting driver " + chosenDriverId + " for request " + chosenRequestId);
            daoTrip.setDriverOnTrip(chosenRequestId, chosenDriverId);
            logger.info("requesting all trips and unset requests");
            List<TripEntity> allTrips = daoTrip.getAllTrips();
            req.setAttribute(RequestParametersNames.TRIPS, allTrips);
            return ConfigurationManager.getProperty(PageNamesConstants.TRIP_LIST);
        } catch (DAOException e) {
            throw new ActionExecutionException("error during setting driver on trip", e);
        }
        //TODO: make drivers see only applications that are not binded to any drivers
        //TODO: make a message when admin is not online
        //TODO: internationalize exceptional messages and JSP text
    }
}
