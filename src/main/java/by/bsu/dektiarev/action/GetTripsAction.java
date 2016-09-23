package by.bsu.dektiarev.action;

import by.bsu.dektiarev.action.util.OffsetParameterOperator;
import by.bsu.dektiarev.dao.TripDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.Trip;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action, responsible for providing all trips
 * Created by USER on 25.04.2016.
 */
public class GetTripsAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        LOG.info("requesting all trips");
        try {
            TripDAO tripDAO = (TripDAO) daoFactory.getDAOFromFactory(DAOType.TRIP);
            Integer numberOfTrips = tripDAO.getNumberOfTrips();
            int offset = OffsetParameterOperator.processOffsetParameter(req, numberOfTrips);
            List<Trip> allTrips = tripDAO.getAllTrips(offset);
            req.setAttribute(RequestParameterName.TRIPS, allTrips);
            return PagesBundleManager.getProperty(PageNameConstant.TRIP_LIST);
        } catch (DAOException ex) {
            throw new ActionExecutionException(ex.getMessage());
        }

    }
}
