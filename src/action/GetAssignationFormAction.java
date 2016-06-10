package action;

import dao.DriverDAO;
import dao.RequestDAO;
import entity.DriverEntity;
import entity.RequestEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.PageNameConstant;
import util.PagesBundleManager;
import util.RequestParameterName;

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
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("requesting setting form: available applications and drivers");
        RequestDAO requestDAO = new RequestDAO();
        DriverDAO driverDAO = new DriverDAO();
        List<RequestEntity> unsetApplications = requestDAO.getUnassignedRequests();
        List<DriverEntity> driverEntityList = driverDAO.getDriversWithHealthyAutos();
        req.setAttribute(RequestParameterName.REQUESTS, unsetApplications);
        req.setAttribute(RequestParameterName.DRIVERS, driverEntityList);
        return PagesBundleManager.getProperty(PageNameConstant.ASSIGNATION_FORM);
    }
}
