package action;

import action.bean.ActionResponse;
import action.bean.ActionType;
import bean.RequestViewBean;
import dao.RequestDAO;
import dao.util.RequestViewBeanListProvider;
import entity.RequestEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;
import util.RequestParametersNames;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Get all the requests.
 * Created by USER on 25.04.2016.
 */
public class GetRequestsAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public ActionResponse execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("requesting all requests");
        RequestDAO requestDAO = new RequestDAO();
        List<RequestEntity> allRequests = requestDAO.getAllRequests();
        List<RequestViewBean> requestViewBeanList =
                RequestViewBeanListProvider.createRequestViewBeanList(allRequests);
        req.setAttribute(RequestParametersNames.REQUESTS, requestViewBeanList);
        return new ActionResponse(ConfigurationManager.getProperty(PageNamesConstants.REQUESTS),
                ActionType.FORWARD);
    }
}
