package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.DriverDAO;
import by.bsu.dektiarev.entity.Driver;
import by.bsu.dektiarev.entity.Request;
import by.bsu.dektiarev.entity.Truck;
import by.bsu.dektiarev.entity.User;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessage;
import by.bsu.dektiarev.util.PasswordEncryptor;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class DriverDAOJPAImpl extends GenericDAOJPAImpl implements DriverDAO {

    private static final String GET_ALL_QUERY = "Driver.getAll";
    private static final String GET_DRIVERS_HEALTHY_TRUCKS_QUERY = "Driver.getDriversWithHealthyTrucks";
    private static final String SEARCH_FOR_DRIVER_QUERY = "Driver.searchByRequest";
    private static final String REQUEST_PARAMETER = "request";

    @Override
    public List<Driver> getAllDrivers() {
        TypedQuery<Driver> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, Driver.class);
        return namedQuery.getResultList();
    }

    @Override
    public List<Driver> getDriversWithHealthyTrucks() {
        TypedQuery<Driver> namedQuery = getManager().createNamedQuery(GET_DRIVERS_HEALTHY_TRUCKS_QUERY,
                Driver.class);
        return namedQuery.getResultList();
    }

    @Override
    public Driver searchByUser(User user) {
        return getManager().find(Driver.class, user.getId());
    }

    @Override
    public Driver searchByRequest(Request request) {
        TypedQuery<Driver> namedQuery = getManager().createNamedQuery(SEARCH_FOR_DRIVER_QUERY, Driver.class);
        namedQuery.setParameter(REQUEST_PARAMETER, request);
        List<Driver> driverList = namedQuery.getResultList();
        if(driverList.isEmpty()) {
            return null;
        } else {
            return driverList.get(0);
        }
    }

    @Override
    public void addNewDriver(String login, String password, Truck truck) throws DAOException {
        if(login == null || password == null || "".equals(login) || "".equals(password)) {
            throw new DAOException(ExceptionalMessage.WRONG_INPUT_PARAMETERS);
        }
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            Driver driver = new Driver();
            String encryptedPassword = PasswordEncryptor.encryptPassword(password);
            driver.setPassword(encryptedPassword);
            driver.setLogin(login);
            driver.setTruck(truck);
            getManager().persist(driver);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.DML_EXCEPTION);
        } finally {
            getManager().clear();
        }
    }
}
