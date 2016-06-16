package action;

import dao.TruckDAO;
import dao.util.DAOFactory;
import dao.util.DAOType;
import entity.Truck;
import exception.ActionExecutionException;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action, responsible for providing information about all trucks
 * Created by USER on 25.04.2016.
 */
public class GetTrucksAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        logger.info("requesting all trucks");
        try {
            TruckDAO truckDAO = (TruckDAO) DAOFactory.getDAOFromFactory(DAOType.TRUCK);
            List<Truck> allTrucks = truckDAO.getAllTrucks();
            req.setAttribute(RequestParameterName.TRUCKS, allTrucks);
            return PagesBundleManager.getProperty(PageNameConstant.TRUCKS);
        } catch (DAOException ex) {
            throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                    ex.getMessage(),
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
        }
    }
}
