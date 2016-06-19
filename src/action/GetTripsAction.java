package action;

import dao.TripDAO;
import dao.util.DAOFactory;
import dao.util.DAOType;
import entity.Trip;
import exception.ActionExecutionException;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action, responsible for providing all trips
 * Created by USER on 25.04.2016.
 */
public class GetTripsAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        LOG.info("requesting all trips");
        try {
            TripDAO tripDAO = (TripDAO) DAOFactory.getDAOFromFactory(DAOType.TRIP);
            List<Trip> allTrips = tripDAO.getAllTrips();
            req.setAttribute(RequestParameterName.TRIPS, allTrips);
            return PagesBundleManager.getProperty(PageNameConstant.TRIP_LIST);
        } catch (DAOException ex) {
            throw new ActionExecutionException(ex.getMessage());
        }

    }
}
