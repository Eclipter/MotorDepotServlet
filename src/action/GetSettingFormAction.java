package action;

import dao.RequestDAO;
import dao.DriverDAO;
import entity.RequestEntity;
import entity.DriverEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Get setting form for trips.
 * Created by USER on 26.04.2016.
 */
public class GetSettingFormAction implements Action  {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("requesting setting form: available applications and drivers");
        RequestDAO requestDAO = new RequestDAO();
        DriverDAO driverDAO = new DriverDAO();
        List<RequestEntity> unsetApplications = requestDAO.getUnsetRequests();
        List<DriverEntity> driverEntityList = driverDAO.getDriversWithHealthyAutos();
        req.setAttribute("applications", unsetApplications);
        req.setAttribute("drivers", driverEntityList);
        return ConfigurationManager.getProperty(PageNamesConstants.SET_FORM);
    }
}
