package action;

import dao.TripDAO;
import entity.TripEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.PageNameConstant;
import util.PagesBundleManager;
import util.RequestParameterName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action, responsible for providing all trips
 * Created by USER on 25.04.2016.
 */
public class GetTripsAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("requesting all trips");
        TripDAO daoTrip = new TripDAO();
        List<TripEntity> allTrips = daoTrip.getAllTrips();
        req.setAttribute(RequestParameterName.TRIPS, allTrips);
        return PagesBundleManager.getProperty(PageNameConstant.TRIP_LIST);
    }
}
