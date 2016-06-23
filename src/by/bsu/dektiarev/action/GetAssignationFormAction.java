package by.bsu.dektiarev.action;

import by.bsu.dektiarev.dao.DriverDAO;
import by.bsu.dektiarev.dao.RequestDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.Driver;
import by.bsu.dektiarev.entity.Request;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action, responsible for preparing and providing an assignation form
 * Created by USER on 26.04.2016.
 */
public class GetAssignationFormAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        LOG.info("requesting setting form: available applications and drivers");
        try {
            RequestDAO requestDAO = (RequestDAO) DAOFactory.getDAOFromFactory(DAOType.REQUEST);
            DriverDAO driverDAO = (DriverDAO) DAOFactory.getDAOFromFactory(DAOType.DRIVER);
            List<Request> unsetApplications = requestDAO.getUnassignedRequests();
            List<Driver> driverList = driverDAO.getDriversWithHealthyTrucks();
            req.setAttribute(RequestParameterName.REQUESTS, unsetApplications);
            req.setAttribute(RequestParameterName.DRIVERS, driverList);
            return PagesBundleManager.getProperty(PageNameConstant.ASSIGNATION_FORM);
        } catch (DAOException ex) {
            throw new ActionExecutionException(ex.getMessage());
        }
    }
}
