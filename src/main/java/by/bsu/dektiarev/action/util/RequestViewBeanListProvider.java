package by.bsu.dektiarev.action.util;

import by.bsu.dektiarev.bean.RequestViewBean;
import by.bsu.dektiarev.dao.DriverDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.Driver;
import by.bsu.dektiarev.entity.Request;
import by.bsu.dektiarev.exception.DAOException;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class used to convert lists of RequestEntities to RequestViewBean lists (includes drivers if present)
 * Created by USER on 09.06.2016.
 */
public final class RequestViewBeanListProvider {

    /**
     * Finds drivers for each request and includes them into result beans
     * @param requestList list to convert
     * @return list of view beans
     */
    public static List<RequestViewBean> createRequestViewBeanList(List<Request> requestList) throws DAOException {
        DriverDAO driverDAO = (DriverDAO) DAOFactory.getInstance().getDAOFromFactory(DAOType.DRIVER);
        List<RequestViewBean> requestViewBeanList = new ArrayList<>();
        for(Request request : requestList) {
            Driver driver = driverDAO.find(request);
            if(driver == null) {
                requestViewBeanList.add(new RequestViewBean(request));
            }
            else {
                requestViewBeanList.add(new RequestViewBean(request, driver));
            }
        }
        return requestViewBeanList;
    }

    private RequestViewBeanListProvider() {
    }
}
