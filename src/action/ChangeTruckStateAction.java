package action;

import dao.TruckDAO;
import entity.State;
import entity.TruckEntity;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;

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
        Integer chosenTruckParameter = Integer.valueOf(req.getParameter("chosenTruck"));
        String chosenStateParameter = req.getParameter("chosenState");
        TruckDAO daoTruck = new TruckDAO();

        try {
            logger.info("changing truck " + chosenTruckParameter + " state to " + chosenStateParameter);
            daoTruck.changeTruckState(chosenTruckParameter, State.valueOf(chosenStateParameter));
        } catch (DAOException e) {
            logger.error("error during changing truck state", e);
            req.setAttribute("errorMessage", e.getMessage());
            return ConfigurationManager.getProperty("error");
        }

        logger.info("requesting all trucks");
        List<TruckEntity> allAutos = daoTruck.getAllTrucks();
        req.setAttribute("trucks", allAutos);
        return ConfigurationManager.getProperty(PageNamesConstants.TRUCKS);
    }
}
