package action;

import action.bean.ActionResponse;
import action.bean.ActionType;
import dao.RequestDAO;
import dao.DriverDAO;
import entity.RequestEntity;
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
 * Get setting form for trips.
 * Created by USER on 26.04.2016.
 */
public class GetAssignationFormAction implements Action  {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public ActionResponse execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("requesting setting form: available applications and drivers");
        RequestDAO requestDAO = new RequestDAO();
        DriverDAO driverDAO = new DriverDAO();
        List<RequestEntity> unsetApplications = requestDAO.getUnassignedRequests();
        List<DriverEntity> driverEntityList = driverDAO.getDriversWithHealthyAutos();
        req.setAttribute(RequestParametersNames.REQUESTS, unsetApplications);
        req.setAttribute(RequestParametersNames.DRIVERS, driverEntityList);
        return new ActionResponse(ConfigurationManager.getProperty(PageNamesConstants.ASSIGNATION_FORM),
                ActionType.FORWARD);
    }
}
