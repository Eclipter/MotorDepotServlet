package action;

import action.bean.ActionResponse;
import action.bean.ActionType;
import dao.DriverDAO;
import entity.DriverEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;
import util.RequestParametersNames;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Get all drivers
 * Created by USER on 25.04.2016.
 */
public class GetDriversAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public ActionResponse execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("requesting all drivers");
        DriverDAO driverDAO = new DriverDAO();
        List<DriverEntity> allDrivers = driverDAO.getAllDrivers();
        req.setAttribute(RequestParametersNames.DRIVERS, allDrivers);
        return new ActionResponse(ConfigurationManager.getProperty(PageNamesConstants.DRIVERS),
                ActionType.FORWARD);
    }
}
