package by.bsu.dektiarev.action;

import by.bsu.dektiarev.dao.TripDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.RequestParameterName;
import by.bsu.dektiarev.util.URLConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action, responsible for changing trip state
 * Created by USER on 15.05.2016.
 */
public class ChangeTripStateAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            String tripIdString = req.getParameter(RequestParameterName.TRIP_ID);
            String chosenState = req.getParameter(RequestParameterName.CHOSEN_STATE);
            if (tripIdString == null || chosenState == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            Integer tripId = Integer.valueOf(tripIdString);
            TripDAO tripDAO = (TripDAO) DAOFactory.getDAOFromFactory(DAOType.TRIP);
            LOG.info("changing trip " + tripId + " state to " + chosenState);
            tripDAO.changeTripState(tripId, chosenState.equals("true"));
            return URLConstant.GET_TRIPS;
        } catch (DAOException e) {
            throw new ActionExecutionException(e.getMessage());
        }
    }
}
