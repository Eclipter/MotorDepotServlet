package action;

import dao.DAOTrip;
import entity.TripEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Get all trips.
 * Created by USER on 25.04.2016.
 */
public class GetTripsAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("requesting all trips");
        DAOTrip daoTrip = new DAOTrip();
        List<TripEntity> allTrips = daoTrip.getAllTrips();
        req.setAttribute("trips", allTrips);
        return ConfigurationManager.getProperty("trip_list");
    }
}
