package action;

import dao.TripDAO;
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
 * Action, responsible for changing trip state
 * Created by USER on 15.05.2016.
 */
public class ChangeTripStateAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            String tripIdString = req.getParameter(RequestParameterName.TRIP_ID);
            String chosenState = req.getParameter(RequestParameterName.CHOSEN_STATE);
            if(tripIdString == null || chosenState == null) {
                throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                        ExceptionalMessage.MISSING_REQUEST_PARAMETERS,
                        (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
            }
            Integer tripId = Integer.valueOf(tripIdString);
            TripDAO daoTrip = new TripDAO();
            logger.info("changing trip " + tripId + " state to " + chosenState);
            daoTrip.changeTripState(tripId, chosenState.equals("true"));
            return URLConstant.GET_TRIPS;
        } catch (DAOException e) {
            throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                    e.getMessage(),
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
        }
    }
}
