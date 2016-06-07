package action;

import dao.DAOApplication;
import dao.DAODriver;
import entity.ApplicationEntity;
import entity.DriverEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;

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
        DAOApplication daoApplication = new DAOApplication();
        DAODriver daoDriver = new DAODriver();
        List<ApplicationEntity> unsetApplications = daoApplication.getUnsetApplications();
        List<DriverEntity> driverEntityList = daoDriver.getDriversWithHealthyAutos();
        req.setAttribute("applications", unsetApplications);
        req.setAttribute("drivers", driverEntityList);
        return ConfigurationManager.getProperty("set_form");
    }
}
