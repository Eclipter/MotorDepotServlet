package dao.util;

import bean.RequestViewBean;
import dao.RequestDAO;
import entity.DriverEntity;
import entity.RequestEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class used to convert lists of RequestEntities to RequestViewBean lists (includes drivers if present)
 * Created by USER on 09.06.2016.
 */
public final class RequestViewBeanListProvider {

    /**
     * Finds drivers for each request and includes them into result beans
     * @param requestEntityList lis to convert
     * @return list of view beans
     */
    public static List<RequestViewBean> createRequestViewBeanList(List<RequestEntity> requestEntityList) {
        RequestDAO requestDAO = new RequestDAO();
        List<RequestViewBean> requestViewBeanList = new ArrayList<>();
        for(RequestEntity requestEntity : requestEntityList) {
            List<DriverEntity> driverEntityList = requestDAO.searchForDriverCompletingRequest(requestEntity);
            if(driverEntityList.isEmpty()) {
                requestViewBeanList.add(new RequestViewBean(requestEntity));
            }
            else {
                requestViewBeanList.add(new RequestViewBean(requestEntity, driverEntityList.get(0)));
            }
        }
        return requestViewBeanList;
    }

    private RequestViewBeanListProvider() {
    }
}
