package by.bsu.dektiarev.action.util;

import by.bsu.dektiarev.exception.ActionExecutionException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import by.bsu.dektiarev.util.RequestParameterName;

import javax.servlet.http.HttpServletRequest;

import static by.bsu.dektiarev.dao.GenericDAO.COLLECTION_DEFAULT_LIMIT;
import static by.bsu.dektiarev.dao.GenericDAO.COLLECTION_MAX_LIMIT;

/**
 * Util class used to check the offset parameter for collection fetching,
 * change this parameter and add it to the request attributes
 * Created by USER on 22.09.2016.
 */
public final class PaginationParametersOperator {

    /**
     *
     * @param req servlet request
     * @param collectionSize size of the actual collection to select from
     * @return correct offset parameter
     * @throws ActionExecutionException in case of error while parsing offset parameter
     */
    public static int processOffsetParameter(HttpServletRequest req, int collectionSize, int fetchLimit)
            throws ActionExecutionException {
        int startFrom = 0;
        String startFromParameter = req.getParameter(RequestParameterName.START_FROM);
        if(startFromParameter != null) {
            try {
                startFrom = Integer.parseInt(startFromParameter);
            } catch (NumberFormatException ex) {
                throw new ActionExecutionException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
            }
            if(startFrom <= 0) {
                startFrom = 0;
            }
            else if(startFrom >= collectionSize) {
                startFrom = collectionSize - collectionSize % fetchLimit;
            }
        }
        req.setAttribute(RequestParameterName.START_FROM, startFrom);
        req.setAttribute(RequestParameterName.LIST_SIZE, collectionSize);
        return startFrom;
    }

    public static int processFetchLimitParameter(HttpServletRequest req) throws ActionExecutionException {
        String fetchLimitParameter = req.getParameter(RequestParameterName.FETCH_LIMIT);
        if(fetchLimitParameter != null) {
            int fetchLimit;
            try {
                fetchLimit = Integer.parseInt(fetchLimitParameter);
            } catch (NumberFormatException ex) {
                throw new ActionExecutionException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
            }
            if(fetchLimit > 0 && fetchLimit < COLLECTION_MAX_LIMIT) {
                req.setAttribute(RequestParameterName.FETCH_LIMIT, fetchLimit);
                return fetchLimit;
            }
        }
        req.setAttribute(RequestParameterName.FETCH_LIMIT, COLLECTION_DEFAULT_LIMIT);
        return COLLECTION_DEFAULT_LIMIT;
    }

    private PaginationParametersOperator() {
    }
}
