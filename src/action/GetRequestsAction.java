package action;

import dao.RequestDAO;
import entity.RequestEntity;
import bean.RequestViewBean;
import entity.DriverEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Get all the requests.
 * Created by USER on 25.04.2016.
 */
public class GetRequestsAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("requesting all requests");
        RequestDAO requestDAO = new RequestDAO();
        List<RequestEntity> allApplications = requestDAO.getAllRequests();
        List<RequestViewBean> requestViewBeanList = new ArrayList<>();
        for(RequestEntity requestEntity : allApplications) {
            List<DriverEntity> driverEntityList = requestDAO.searchForDriverCompletingRequest(requestEntity);
            if(driverEntityList.isEmpty()) {
                requestViewBeanList.add(new RequestViewBean(requestEntity));
            }
            else {
                requestViewBeanList.add(new RequestViewBean(requestEntity, driverEntityList.get(0)));
            }
        }
        req.setAttribute("requests", requestViewBeanList);
        return ConfigurationManager.getProperty("requests");
    }
}
