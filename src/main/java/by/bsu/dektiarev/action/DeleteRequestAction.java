package by.bsu.dektiarev.action;

import by.bsu.dektiarev.dao.RequestDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import by.bsu.dektiarev.util.RequestParameterName;
import by.bsu.dektiarev.util.URLConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by USER on 20.07.2016.
 */
public class DeleteRequestAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            String requestIdParameter = req.getParameter(RequestParameterName.REQUEST_ID);
            if(requestIdParameter == null) {
                throw new ActionExecutionException(ExceptionalMessageKey.MISSING_REQUEST_PARAMETERS);
            }
            Integer requestId;
            try {
                requestId = Integer.valueOf(requestIdParameter);
            } catch (NumberFormatException e) {
                throw new ActionExecutionException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
            }
            if (requestId <= 0) {
                throw new ActionExecutionException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
            }
            RequestDAO requestDAO = (RequestDAO) daoFactory.getDAOFromFactory(DAOType.REQUEST);
            LOG.info("deleting request {}", requestId);
            requestDAO.deleteRequest(requestId);
            return URLConstant.GET_REQUESTS;
        } catch (DAOException e) {
            throw new ActionExecutionException(e.getMessage());
        }
    }
}
