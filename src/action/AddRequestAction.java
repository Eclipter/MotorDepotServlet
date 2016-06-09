package action;

import action.bean.ActionResponse;
import action.bean.ActionType;
import dao.RequestDAO;
import exception.ActionExecutionException;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.RequestParametersNames;
import util.URLConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by USER on 09.06.2016.
 */
public class AddRequestAction implements Action {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public ActionResponse execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
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
            return new ActionResponse(URLConstants.GET_REQUESTS, ActionType.REDIRECT);
        } catch (DAOException e) {
            throw new ActionExecutionException("error during registering new request", e);
        }
    }
}
