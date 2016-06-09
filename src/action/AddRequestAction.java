package action;

import bean.RequestViewBean;
import dao.RequestDAO;
import dao.util.RequestViewBeanListProvider;
import entity.RequestEntity;
import exception.ActionExecutionException;
import exception.DAOException;
import exception.ExceptionalMessage;
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
public class AddRequestAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            String cargoWeightParameter = req.getParameter(RequestParametersNames.CARGO_WEIGHT);
            if(cargoWeightParameter == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            Integer cargoWeight;
            try {
                cargoWeight = Integer.valueOf(cargoWeightParameter);
            } catch (NumberFormatException e) {
                throw new ActionExecutionException(ExceptionalMessage.WRONG_INPUT_FOR_WEIGHT);
            }
            if(cargoWeight <= 0) {
                throw new ActionExecutionException(ExceptionalMessage.WRONG_INPUT_FOR_WEIGHT);
            }
            RequestDAO requestDAO = new RequestDAO();
            logger.info("adding new request with cargo weight: " + cargoWeight);
            requestDAO.registerNewRequest(cargoWeight);
            logger.info("requesting all requests");
            List<RequestEntity> requestEntityList = requestDAO.getAllRequests();
            List<RequestViewBean> requestViewBeanList =
                    RequestViewBeanListProvider.createRequestViewBeanList(requestEntityList);
            req.setAttribute(RequestParametersNames.REQUESTS, requestViewBeanList);
            return ConfigurationManager.getProperty(PageNamesConstants.REQUESTS);
        } catch (DAOException e) {
            throw new ActionExecutionException("error during registering new request", e);
        }
    }
}
