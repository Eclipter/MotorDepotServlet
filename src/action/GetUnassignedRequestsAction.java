package action;

import action.util.RequestViewBeanListProvider;
import bean.RequestViewBean;
import dao.RequestDAO;
import dao.util.DAOFactory;
import dao.util.DAOType;
import entity.Request;
import exception.ActionExecutionException;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/** Action, responsible for providing information about unassigned requests
 * Created by USER on 09.06.2016.
 */
public class GetUnassignedRequestsAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            logger.info("requesting all unset requests");
            RequestDAO requestDAO = (RequestDAO) DAOFactory.getDAOFromFactory(DAOType.REQUEST);
            List<Request> unsetRequests = requestDAO.getUnassignedRequests();
            List<RequestViewBean> requestViewBeanList =
                    RequestViewBeanListProvider.createRequestViewBeanList(unsetRequests);
            req.setAttribute(RequestParameterName.REQUESTS, requestViewBeanList);
            return PagesBundleManager.getProperty(PageNameConstant.REQUESTS);
        } catch (DAOException e) {
            throw new ActionExecutionException(InternationalizedBundleManager.getProperty(BundleName.ERROR_MESSAGE,
                    e.getMessage(),
                    (String) req.getSession().getAttribute(RequestParameterName.LANGUAGE)));
        }
    }
}
