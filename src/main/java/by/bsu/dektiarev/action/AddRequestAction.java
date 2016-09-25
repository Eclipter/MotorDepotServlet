package by.bsu.dektiarev.action;

import by.bsu.dektiarev.dao.RequestDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
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
                throw new ActionExecutionException(ExceptionalMessageKey.MISSING_REQUEST_PARAMETERS);
            }
            double cargoWeight;
            int departureStationId;
            int destinationStationId;
            try {
                cargoWeight = Double.parseDouble(cargoWeightParameter);
                departureStationId = Integer.parseInt(departurePointParameter);
                destinationStationId = Integer.parseInt(destinationPointParameter);
            } catch (NumberFormatException e) {
                throw new ActionExecutionException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
            }
            if (cargoWeight <= 0) {
                throw new ActionExecutionException(ExceptionalMessageKey.WRONG_INPUT_FOR_WEIGHT);
            }
            if(departureStationId <= 0 || destinationStationId <= 0 ||
                    Objects.equals(departureStationId, destinationStationId)) {
                throw new ActionExecutionException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
            }
            RequestDAO requestDAO = (RequestDAO) daoFactory.getDAOFromFactory(DAOType.REQUEST);
            LOG.info("adding new request with cargo weight: " + cargoWeight);
            requestDAO.addRequest(departureStationId, destinationStationId, cargoWeight);
            return URLConstant.GET_REQUESTS;
        } catch (DAOException e) {
            throw new ActionExecutionException(e.getMessage());
        }
    }
}
