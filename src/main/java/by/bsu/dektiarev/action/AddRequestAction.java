package by.bsu.dektiarev.action;

import by.bsu.dektiarev.dao.RequestDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.RequestParameterName;
import by.bsu.dektiarev.util.URLConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Action, responsible for adding new request to the database
 * Created by USER on 09.06.2016.
 */
public class AddRequestAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            String cargoWeightParameter = req.getParameter(RequestParameterName.CARGO_WEIGHT);
            String departurePointParameter = req.getParameter(RequestParameterName.DEPARTURE_STATION_ID);
            String destinationPointParameter = req.getParameter(RequestParameterName.DESTINATION_STATION_ID);
            if (cargoWeightParameter == null || departurePointParameter == null || destinationPointParameter == null) {
                throw new ActionExecutionException(ExceptionalMessage.MISSING_REQUEST_PARAMETERS);
            }
            Double cargoWeight;
            Integer departureStationId;
            Integer destinationStationId;
            try {
                cargoWeight = Double.valueOf(cargoWeightParameter);
                departureStationId = Integer.valueOf(departurePointParameter);
                destinationStationId = Integer.valueOf(destinationPointParameter);
            } catch (NumberFormatException e) {
                throw new ActionExecutionException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
            }
            if (cargoWeight <= 0) {
                throw new ActionExecutionException(ExceptionalMessage.WRONG_INPUT_FOR_WEIGHT);
            }
            if(departureStationId <= 0 || destinationStationId <= 0 ||
                    Objects.equals(departureStationId, destinationStationId)) {
                throw new ActionExecutionException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
            }
            RequestDAO requestDAO = (RequestDAO) daoFactory.getDAOFromFactory(DAOType.REQUEST);
            LOG.info("adding new request with cargo weight: " + cargoWeight);
            requestDAO.addNewRequest(departureStationId, destinationStationId, cargoWeight);
            return URLConstant.GET_REQUESTS;
        } catch (DAOException e) {
            throw new ActionExecutionException(e.getMessage());
        }
    }
}
