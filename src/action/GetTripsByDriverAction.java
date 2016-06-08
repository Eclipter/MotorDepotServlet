package action;

import bean.UserInfoBean;
import dao.TripDAO;
import entity.TripEntity;
import exception.ActionExecutionException;
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
 * Get all trips of a particular driver.
 * Created by USER on 26.04.2016.
 */
public class GetTripsByDriverAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        TripDAO daoTrip = new TripDAO();
        UserInfoBean userInfoBean = (UserInfoBean) req.getSession().getAttribute("user");
        if(userInfoBean == null) {
            throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
        }
        Integer driverId = userInfoBean.getUserEntity().getId();
        logger.info("requesting trips of driver " + driverId);
        List<TripEntity> allTrips = daoTrip.getTripsByDriver(driverId);
        req.setAttribute(RequestParametersNames.TRIPS, allTrips);
        return ConfigurationManager.getProperty(PageNamesConstants.TRIP_LIST);
    }
}
