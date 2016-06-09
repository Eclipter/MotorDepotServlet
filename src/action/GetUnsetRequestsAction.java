package action;

import bean.RequestViewBean;
import dao.RequestDAO;
import dao.util.RequestViewBeanListProvider;
import entity.RequestEntity;
import exception.ActionExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConfigurationManager;
import util.PageNamesConstants;
import util.RequestParametersNames;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by USER on 09.06.2016.
 */
public class GetUnsetRequestsAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        logger.info("requesting all unset requests");
        RequestDAO requestDAO = new RequestDAO();
        List<RequestEntity> unsetRequests = requestDAO.getUnsetRequests();
        List<RequestViewBean> requestViewBeanList =
                RequestViewBeanListProvider.createRequestViewBeanList(unsetRequests);
        req.setAttribute(RequestParametersNames.REQUESTS, requestViewBeanList);
        return ConfigurationManager.getProperty(PageNamesConstants.REQUESTS);
    }
}
