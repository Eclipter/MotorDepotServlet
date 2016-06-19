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

/**
 * Action, responsible for providing page with all requests
 * Created by USER on 25.04.2016.
 */
public class GetRequestsAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            LOG.info("requesting all requests");
            RequestDAO requestDAO = (RequestDAO) DAOFactory.getDAOFromFactory(DAOType.REQUEST);
            List<Request> allRequests = requestDAO.getAllRequests();
            List<RequestViewBean> requestViewBeanList =
                    RequestViewBeanListProvider.createRequestViewBeanList(allRequests);
            req.setAttribute(RequestParameterName.REQUESTS, requestViewBeanList);
            return PagesBundleManager.getProperty(PageNameConstant.REQUESTS);
        } catch (DAOException e) {
            throw new ActionExecutionException(e.getMessage());
        }
    }
}
