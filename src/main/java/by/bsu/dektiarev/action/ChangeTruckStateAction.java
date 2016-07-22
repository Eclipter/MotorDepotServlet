package by.bsu.dektiarev.action;

import by.bsu.dektiarev.bean.UserInfoBean;
import by.bsu.dektiarev.dao.TruckDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.util.TruckState;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessage;
import by.bsu.dektiarev.util.RequestParameterName;
import by.bsu.dektiarev.util.URLConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action, responsible for changing truck state
 * Created by USER on 25.04.2016.
 */
public class ChangeTruckStateAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {

        try {
            String chosenStateParameter = req.getParameter(RequestParameterName.CHOSEN_STATE);
            if (chosenStateParameter == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            TruckDAO truckDAO = (TruckDAO) daoFactory.getDAOFromFactory(DAOType.TRUCK);
            UserInfoBean currentUser = (UserInfoBean) req.getSession().getAttribute(RequestParameterName.USER);
            Integer driverId = currentUser.getUser().getId();
            Integer chosenTruck = truckDAO.getTruckByDriver(driverId).getId();
            LOG.info("changing truck " + chosenTruck + " state to " + chosenStateParameter);
            truckDAO.changeTruckState(chosenTruck, TruckState.valueOf(chosenStateParameter));
            return URLConstant.GET_TRUCKS;
        } catch (DAOException e) {
            throw new ActionExecutionException(e.getMessage());
        } catch (NumberFormatException e) {
            LOG.warn("wrong input for truck id");
            throw new ActionExecutionException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        } catch (IllegalArgumentException e) {
            LOG.warn("wrong input for truck state");
            throw new ActionExecutionException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
    }
}
