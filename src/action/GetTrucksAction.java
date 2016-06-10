package action;

import dao.TruckDAO;
import entity.TruckEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.PageNameConstant;
import util.PagesBundleManager;
import util.RequestParameterName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Get all autos.
 * Created by USER on 25.04.2016.
 */
public class GetTrucksAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("requesting all trucks");
        TruckDAO daoTruck = new TruckDAO();
        List<TruckEntity> allTrucks = daoTruck.getAllTrucks();
        req.setAttribute(RequestParameterName.TRUCKS, allTrucks);
        return PagesBundleManager.getProperty(PageNameConstant.TRUCKS);
    }
}
