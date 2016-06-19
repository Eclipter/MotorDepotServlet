package action;

import dao.RequestDAO;
import dao.util.DAOFactory;
import dao.util.DAOType;
import exception.ActionExecutionException;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.RequestParameterName;
import util.URLConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action, responsible for adding new request to the database
 * Created by USER on 09.06.2016.
 */
public class AddRequestAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            String cargoWeightParameter = req.getParameter(RequestParameterName.CARGO_WEIGHT);
            if (cargoWeightParameter == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            Integer cargoWeight;
            try {
                cargoWeight = Integer.valueOf(cargoWeightParameter);
            } catch (NumberFormatException e) {
                throw new ActionExecutionException(ExceptionalMessage.WRONG_INPUT_FOR_WEIGHT);
            }
            if (cargoWeight <= 0) {
                throw new ActionExecutionException(ExceptionalMessage.WRONG_INPUT_FOR_WEIGHT);
            }
            RequestDAO requestDAO = (RequestDAO) DAOFactory.getDAOFromFactory(DAOType.REQUEST);
            LOG.info("adding new request with cargo weight: " + cargoWeight);
            requestDAO.addNewRequest(cargoWeight);
            return URLConstant.GET_REQUESTS;
        } catch (DAOException e) {
            throw new ActionExecutionException(e.getMessage());
        }
    }
}
