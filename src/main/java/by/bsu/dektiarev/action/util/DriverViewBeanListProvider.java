package by.bsu.dektiarev.action.util;

import by.bsu.dektiarev.bean.DriverViewBean;
import by.bsu.dektiarev.dao.TripDAO;
import by.bsu.dektiarev.dao.util.DAOFactory;
import by.bsu.dektiarev.dao.util.DAOType;
import by.bsu.dektiarev.entity.Driver;
import by.bsu.dektiarev.exception.DAOException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 01.10.2016.
 */
public final class DriverViewBeanListProvider {

    public static List<DriverViewBean> createDriverViewBeanList(List<Driver> driverList) throws DAOException {
        TripDAO tripDAO = (TripDAO) DAOFactory.getInstance().getDAOFromFactory(DAOType.TRIP);
        List<DriverViewBean> driverViewBeanList = new ArrayList<>();
        for(Driver driver : driverList) {
            Integer numberOfTripsByDriver = tripDAO.getNumberOfTripsByDriver(driver.getId());
            Integer numberOfCompletedTripsByDriver = tripDAO.getNumberOfCompletedTripsByDriver(driver.getId());
            DriverViewBean driverViewBean =
                    new DriverViewBean(driver, numberOfCompletedTripsByDriver, numberOfTripsByDriver);
            driverViewBeanList.add(driverViewBean);
        }
        return driverViewBeanList;
}

    private DriverViewBeanListProvider() {
    }
}
