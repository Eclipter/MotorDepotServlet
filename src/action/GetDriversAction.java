package action;

import dao.DriverDAO;
import dao.util.DAOFactory;
import dao.util.DAOType;
import entity.Driver;
import exception.ActionExecutionException;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action, responsible for providing page with information about all drivers
 * Created by USER on 25.04.2016.
 */
public class GetDriversAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        LOG.info("requesting all drivers");
        try {
            DriverDAO driverDAO = (DriverDAO) DAOFactory.getDAOFromFactory(DAOType.DRIVER);
            List<Driver> allDrivers = driverDAO.getAllDrivers();
            req.setAttribute(RequestParameterName.DRIVERS, allDrivers);
            return PagesBundleManager.getProperty(PageNameConstant.DRIVERS);
        } catch (DAOException ex) {
            throw new ActionExecutionException(ex.getMessage());
        }
    }
}
