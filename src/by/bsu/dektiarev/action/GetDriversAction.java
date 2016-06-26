package by.bsu.dektiarev.action;

import by.bsu.dektiarev.dao.DriverDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.Driver;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action, responsible for providing page with information about all drivers
 * Created by USER on 25.04.2016.
 */
public class GetDriversAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        LOG.info("requesting all drivers");
        try {
            DriverDAO driverDAO = (DriverDAO) daoFactory.getDAOFromFactory(DAOType.DRIVER);
            List<Driver> allDrivers = driverDAO.getAllDrivers();
            req.setAttribute(RequestParameterName.DRIVERS, allDrivers);
            return PagesBundleManager.getProperty(PageNameConstant.DRIVERS);
        } catch (DAOException ex) {
            throw new ActionExecutionException(ex.getMessage());
        }
    }
}
