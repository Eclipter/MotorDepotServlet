package by.bsu.dektiarev.action;

import by.bsu.dektiarev.dao.TripDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.RequestParameterName;
import by.bsu.dektiarev.util.URLConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action, responsible for assigning driver to a trip
 * Created by USER on 26.04.2016.
 */
public class AssignDriverToATripAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            TripDAO tripDAO = (TripDAO) daoFactory.getDAOFromFactory(DAOType.TRIP);
            String requestParameter = req.getParameter(RequestParameterName.CHOSEN_REQUEST);
            String driverParameter = req.getParameter(RequestParameterName.CHOSEN_DRIVER);
            if (requestParameter == null || driverParameter == null) {
                throw new ActionExecutionException(ExceptionalMessageKey.MISSING_REQUEST_PARAMETERS);
            }
            int chosenRequestId = Integer.parseInt(req.getParameter(RequestParameterName.CHOSEN_REQUEST));
            int chosenDriverId = Integer.parseInt(req.getParameter(RequestParameterName.CHOSEN_DRIVER));
            LOG.info("assigning driver " + chosenDriverId + " to request " + chosenRequestId);
            tripDAO.addTrip(chosenRequestId, chosenDriverId);
            return URLConstant.GET_TRIPS;
        } catch (DAOException e) {
            throw new ActionExecutionException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new ActionExecutionException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
    }
}
