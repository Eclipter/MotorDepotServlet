package action;

import dao.TruckDAO;
import entity.State;
import entity.TruckEntity;
import exception.DAOException;
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
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer chosenTruckParameter = Integer.valueOf(req.getParameter(RequestParametersNames.CHOSEN_TRUCK));
        String chosenStateParameter = req.getParameter(RequestParametersNames.CHOSEN_STATE);
        TruckDAO daoTruck = new TruckDAO();

        try {
            logger.info("changing truck " + chosenTruckParameter + " state to " + chosenStateParameter);
            daoTruck.changeTruckState(chosenTruckParameter, State.valueOf(chosenStateParameter));
        } catch (DAOException e) {
            logger.error("error during changing truck state", e);
            req.setAttribute(RequestParametersNames.ERROR_MESSAGE, e.getMessage());
            return ConfigurationManager.getProperty(PageNamesConstants.ERROR);
        }

        logger.info("requesting all trucks");
        List<TruckEntity> allTrucks = daoTruck.getAllTrucks();
        req.setAttribute(RequestParametersNames.TRUCKS, allTrucks);
        return ConfigurationManager.getProperty(PageNamesConstants.TRUCKS);
    }
}
