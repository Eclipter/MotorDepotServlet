package action;

import bean.UserInfoBean;
import dao.TripDAO;
import dao.util.DAOFactory;
import dao.util.DAOType;
import entity.Trip;
import exception.ActionExecutionException;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action, responsible for providing trips of a driver, which is requesting the page
 * Created by USER on 26.04.2016.
 */
public class GetTripsByDriverAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            TripDAO tripDAO = (TripDAO) DAOFactory.getDAOFromFactory(DAOType.TRIP);
            UserInfoBean userInfoBean = (UserInfoBean) req.getSession().getAttribute("user");
            if (userInfoBean == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            Integer driverId = userInfoBean.getUser().getId();
            LOG.info("requesting trips of driver " + driverId);
            List<Trip> allTrips = tripDAO.getTripsByDriver(driverId);
            req.setAttribute(RequestParameterName.TRIPS, allTrips);
            return PagesBundleManager.getProperty(PageNameConstant.TRIP_LIST);
        } catch (DAOException ex) {
            throw new ActionExecutionException(ex.getMessage());
        }

    }
}
