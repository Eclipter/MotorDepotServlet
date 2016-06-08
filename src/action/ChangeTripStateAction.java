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
 * Created by USER on 15.05.2016.
 */
public class ChangeTripStateAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            Integer tripId = Integer.valueOf(req.getParameter(RequestParametersNames.TRIP_ID));
            String chosenState = req.getParameter(RequestParametersNames.CHOSEN_STATE);
            if(tripId == null || chosenState == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            TripDAO daoTrip = new TripDAO();
            logger.info("changing trip " + tripId + " state to " + chosenState);
            daoTrip.changeTripState(tripId, chosenState.equals("true"));
            logger.info("requesting all trips");
            List<TripEntity> allTrips = daoTrip.getAllTrips();
            req.setAttribute(RequestParametersNames.TRIPS, allTrips);
            return ConfigurationManager.getProperty(PageNamesConstants.TRIP_LIST);
        } catch (DAOException e) {
            throw new ActionExecutionException("error during changing trip state", e);
        }
    }
}
