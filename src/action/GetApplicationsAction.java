package action;

import dao.DAOApplication;
import entity.ApplicationEntity;
import entity.ApplicationViewEntity;
import entity.DriverEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Get all the applications.
 * Created by USER on 25.04.2016.
 */
public class GetApplicationsAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("requesting all applications");
        DAOApplication daoApplication = new DAOApplication();
        List<ApplicationEntity> allApplications = daoApplication.getAllApplications();
        List<ApplicationViewEntity> applicationViewEntityList = new ArrayList<>();
        for(ApplicationEntity applicationEntity : allApplications) {
            List<DriverEntity> driverEntityList = daoApplication.searchForDriverCompletingApplication(applicationEntity);
            if(driverEntityList.isEmpty()) {
                applicationViewEntityList.add(new ApplicationViewEntity(applicationEntity));
            }
            else {
                applicationViewEntityList.add(new ApplicationViewEntity(applicationEntity, driverEntityList.get(0)));
            }
        }
        req.setAttribute("applications", applicationViewEntityList);
        return ConfigurationManager.getProperty("applications");
    }
}
