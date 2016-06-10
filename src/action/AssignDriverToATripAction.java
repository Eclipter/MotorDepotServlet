package action;

import dao.TripDAO;
import entity.TripEntity;
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
import java.util.List;

/**
 * Action, responsible for assigning driver to a trip
 * Created by USER on 26.04.2016.
 */
public class AssignDriverToATripAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            TripDAO daoTrip = new TripDAO();
            Integer chosenRequestId = Integer.valueOf(req.getParameter(RequestParameterName.CHOSEN_REQUEST));
            Integer chosenDriverId = Integer.valueOf(req.getParameter(RequestParameterName.CHOSEN_DRIVER));
            if(chosenDriverId == null || chosenRequestId == null) {
                throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                        ExceptionalMessage.MISSING_REQUEST_PARAMETERS,
                        (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
            }
            logger.info("checking parameters");
            List<TripEntity> tripsByDriverAndRequest = daoTrip.getTripByDriverAndRequest(chosenDriverId, chosenRequestId);
            if(!tripsByDriverAndRequest.isEmpty()) {
                throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                        ExceptionalMessage.TRIP_EXISTS,
                        (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
            }
            logger.info("assigning driver " + chosenDriverId + " to request " + chosenRequestId);
            daoTrip.assignDriverToATrip(chosenRequestId, chosenDriverId);
            return URLConstant.GET_TRIPS;
        } catch (DAOException e) {
            throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                    e.getMessage(),
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
        }
    }
}
