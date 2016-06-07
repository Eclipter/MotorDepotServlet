package action;

import dao.DAODriver;
import entity.DriverEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;

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
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("requesting all drivers");
        DAODriver daoDriver = new DAODriver();
        List<DriverEntity> allDrivers = daoDriver.getAllDrivers();
        req.setAttribute("drivers", allDrivers);
        return ConfigurationManager.getProperty("drivers");
    }
}
