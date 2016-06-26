package by.bsu.dektiarev.action;

import by.bsu.dektiarev.dao.TruckDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.Truck;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action, responsible for providing information about all trucks
 * Created by USER on 25.04.2016.
 */
public class GetTrucksAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        LOG.info("requesting all trucks");
        try {
            TruckDAO truckDAO = (TruckDAO) daoFactory.getDAOFromFactory(DAOType.TRUCK);
            List<Truck> allTrucks = truckDAO.getAllTrucks();
            req.setAttribute(RequestParameterName.TRUCKS, allTrucks);
            return PagesBundleManager.getProperty(PageNameConstant.TRUCKS);
        } catch (DAOException ex) {
            throw new ActionExecutionException(ex.getMessage());
        }
    }
}
