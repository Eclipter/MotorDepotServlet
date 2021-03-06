package by.bsu.dektiarev.action;

import by.bsu.dektiarev.action.util.PaginationParametersOperator;
import by.bsu.dektiarev.action.util.RequestViewBeanListProvider;
import by.bsu.dektiarev.bean.RequestViewBean;
import by.bsu.dektiarev.dao.RequestDAO;
import by.bsu.dektiarev.dao.StationDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.Request;
import by.bsu.dektiarev.entity.Station;
import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.bsu.dektiarev.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action, responsible for providing page with all requests
 * Created by USER on 25.04.2016.
 */
public class GetRequestsAction implements Action {

    private static final Logger LOG = LogManager.getLogger();

    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionExecutionException {
        try {
            LOG.info("requesting all requests");
            RequestDAO requestDAO = (RequestDAO) daoFactory.getDAOFromFactory(DAOType.REQUEST);
            StationDAO stationDAO = (StationDAO) daoFactory.getDAOFromFactory(DAOType.STATION);
            List<Station> stationList = stationDAO.getAllStations();
            int numberOfRequests = requestDAO.getNumberOfAllRequests();
            int fetchLimit = PaginationParametersOperator.processFetchLimitParameter(req);
            int offset = PaginationParametersOperator.processOffsetParameter(req, numberOfRequests, fetchLimit);
            List<Request> allRequests = requestDAO.getRequests(offset, fetchLimit);
            List<RequestViewBean> requestViewBeanList =
                    RequestViewBeanListProvider.createRequestViewBeanList(allRequests);
            req.setAttribute(RequestParameterName.STATIONS, stationList);
            req.setAttribute(RequestParameterName.REQUESTS, requestViewBeanList);
            return PagesBundleManager.getProperty(PageNameConstant.REQUESTS);
        } catch (DAOException e) {
            throw new ActionExecutionException(e.getMessage());
        }
    }
}
