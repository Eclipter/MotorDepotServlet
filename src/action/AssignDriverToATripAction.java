package action;

import action.bean.ActionResponse;
import action.bean.ActionType;
import dao.TripDAO;
import entity.TripEntity;
import exception.ActionExecutionException;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.RequestParametersNames;
import util.URLConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Assigns driver to a trip.
 * Created by USER on 26.04.2016.
 */
public class AssignDriverToATripAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public ActionResponse execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

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
            logger.info("assigning driver " + chosenDriverId + " to request " + chosenRequestId);
            daoTrip.assignDriverToATrip(chosenRequestId, chosenDriverId);
            return new ActionResponse(URLConstants.GET_TRIPS, ActionType.REDIRECT);
        } catch (DAOException e) {
            throw new ActionExecutionException("error during setting driver on trip", e);
        }
        //TODO: make a message when admin is not online
        //TODO: internationalize exceptional messages and JSP text
    }
}
