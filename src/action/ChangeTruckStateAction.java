package action;

import dao.TruckDAO;
import entity.util.TruckState;
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
 * Action, responsible for changing truck state
 * Created by USER on 25.04.2016.
 */
public class ChangeTruckStateAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            String chosenTruckParameter = req.getParameter(RequestParameterName.CHOSEN_TRUCK);
            String chosenStateParameter = req.getParameter(RequestParameterName.CHOSEN_STATE);
            if(chosenTruckParameter == null || chosenStateParameter == null) {
                throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                        ExceptionalMessage.MISSING_REQUEST_PARAMETERS,
                        (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
            }
            Integer chosenTruck = Integer.valueOf(chosenTruckParameter);
            TruckDAO truckDAO = new TruckDAO();
            logger.info("changing truck " + chosenTruck + " state to " + chosenStateParameter);
            truckDAO.changeTruckState(chosenTruck, TruckState.valueOf(chosenStateParameter));
            return URLConstant.GET_TRUCKS;
        } catch (DAOException e) {
            throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                    e.getMessage(),
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
        }
    }
}
