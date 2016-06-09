package action;

import action.bean.ActionResponse;
import action.bean.ActionType;
import dao.TripDAO;
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
public class ChangeTripStateAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public ActionResponse execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            String tripIdString = req.getParameter(RequestParametersNames.TRIP_ID);
            String chosenState = req.getParameter(RequestParametersNames.CHOSEN_STATE);
            if(tripIdString == null || chosenState == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            Integer tripId = Integer.valueOf(tripIdString);
            TripDAO daoTrip = new TripDAO();
            logger.info("changing trip " + tripId + " state to " + chosenState);
            daoTrip.changeTripState(tripId, chosenState.equals("true"));
            return new ActionResponse(URLConstants.GET_TRIPS, ActionType.REDIRECT);
        } catch (DAOException e) {
            throw new ActionExecutionException("error during changing trip state", e);
        }
    }
}
