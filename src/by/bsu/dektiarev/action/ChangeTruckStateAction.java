package by.bsu.dektiarev.action;

import by.bsu.dektiarev.dao.TruckDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.util.TruckState;
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
 * Action, responsible for changing truck state
 * Created by USER on 25.04.2016.
 */
public class ChangeTruckStateAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            String chosenTruckParameter = req.getParameter(RequestParameterName.CHOSEN_TRUCK);
            String chosenStateParameter = req.getParameter(RequestParameterName.CHOSEN_STATE);
            if (chosenTruckParameter == null || chosenStateParameter == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            Integer chosenTruck = Integer.valueOf(chosenTruckParameter);
            TruckDAO truckDAO = (TruckDAO) DAOFactory.getDAOFromFactory(DAOType.TRUCK);
            LOG.info("changing truck " + chosenTruck + " state to " + chosenStateParameter);
            truckDAO.changeTruckState(chosenTruck, TruckState.valueOf(chosenStateParameter));
            return URLConstant.GET_TRUCKS;
        } catch (DAOException e) {
            throw new ActionExecutionException(e.getMessage());
        }
    }
}
