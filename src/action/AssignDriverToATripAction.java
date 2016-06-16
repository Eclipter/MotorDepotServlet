package action;

import dao.TripDAO;
import dao.util.DAOFactory;
import dao.util.DAOType;
import exception.ActionExecutionException;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.RequestParameterName;
import util.URLConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action, responsible for assigning driver to a trip
 * Created by USER on 26.04.2016.
 */
public class AssignDriverToATripAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            TripDAO tripDAO = (TripDAO) DAOFactory.getDAOFromFactory(DAOType.TRIP);
            Integer chosenRequestId = Integer.valueOf(req.getParameter(RequestParameterName.CHOSEN_REQUEST));
            Integer chosenDriverId = Integer.valueOf(req.getParameter(RequestParameterName.CHOSEN_DRIVER));
            if(chosenDriverId == null || chosenRequestId == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            logger.info("assigning driver " + chosenDriverId + " to request " + chosenRequestId);
            tripDAO.assignDriverToATrip(chosenRequestId, chosenDriverId);
            return URLConstant.GET_TRIPS;
        } catch (DAOException e) {
            throw new ActionExecutionException(e.getMessage());
        }
    }
}
