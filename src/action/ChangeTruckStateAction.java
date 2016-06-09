package action;

import dao.TruckDAO;
import entity.State;
import entity.TruckEntity;
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
 * Changes state of car.
 * Created by USER on 25.04.2016.
 */
public class ChangeTruckStateAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            String chosenTruckParameter = req.getParameter(RequestParametersNames.CHOSEN_TRUCK);
            String chosenStateParameter = req.getParameter(RequestParametersNames.CHOSEN_STATE);
            if(chosenTruckParameter == null || chosenStateParameter == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            Integer chosenTruck = Integer.valueOf(chosenTruckParameter);
            TruckDAO truckDAO = new TruckDAO();
            logger.info("changing truck " + chosenTruck + " state to " + chosenStateParameter);
            truckDAO.changeTruckState(chosenTruck, State.valueOf(chosenStateParameter));
            logger.info("requesting all trucks");
            List<TruckEntity> allTrucks = truckDAO.getAllTrucks();
            req.setAttribute(RequestParametersNames.TRUCKS, allTrucks);
            return ConfigurationManager.getProperty(PageNamesConstants.TRUCKS);
        } catch (DAOException e) {
            throw new ActionExecutionException("error during changing truck state", e);
        }
    }
}
