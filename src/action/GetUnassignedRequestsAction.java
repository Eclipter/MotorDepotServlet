package action;

import bean.RequestViewBean;
import dao.RequestDAO;
import dao.util.RequestViewBeanListProvider;
import entity.RequestEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.PageNameConstant;
import util.PagesBundleManager;
import util.RequestParameterName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/** Action, responsible for providing information about unassigned requests
 * Created by USER on 09.06.2016.
 */
public class GetUnassignedRequestsAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("requesting all unset requests");
        RequestDAO requestDAO = new RequestDAO();
        List<RequestEntity> unsetRequests = requestDAO.getUnassignedRequests();
        List<RequestViewBean> requestViewBeanList =
                RequestViewBeanListProvider.createRequestViewBeanList(unsetRequests);
        req.setAttribute(RequestParameterName.REQUESTS, requestViewBeanList);
        return PagesBundleManager.getProperty(PageNameConstant.REQUESTS);
    }
}
