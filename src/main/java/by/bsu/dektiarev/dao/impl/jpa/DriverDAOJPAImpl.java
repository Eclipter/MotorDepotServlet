package by.bsu.dektiarev.dao.impl.jpa;

import by.bsu.dektiarev.dao.DriverDAO;
import by.bsu.dektiarev.entity.Driver;
import by.bsu.dektiarev.entity.Request;
import by.bsu.dektiarev.entity.Truck;
import by.bsu.dektiarev.entity.User;
import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessageKey;
import by.bsu.dektiarev.util.PasswordEncryptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class DriverDAOJPAImpl extends GenericDAOJPAImpl implements DriverDAO {

    private static final String GET_ALL_QUERY = "Driver.getAll";
    private static final String GET_NUMBER_QUERY = "Driver.getNumber";
    private static final String GET_DRIVERS_HEALTHY_TRUCKS_QUERY = "Driver.getDriversWithHealthyTrucks";
    private static final String SEARCH_FOR_DRIVER_QUERY = "Driver.find";
    private static final String REQUEST_PARAMETER = "request";

    private static final Logger LOG = LogManager.getLogger();

    public DriverDAOJPAImpl(EntityManager manager) {
        super(manager);
    }

    @Override
    public List<Driver> getDrivers(int offset, int limit) throws DAOException {
        try {
            TypedQuery<Driver> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, Driver.class);
            namedQuery.setMaxResults(limit);
            namedQuery.setFirstResult(offset);
            return namedQuery.getResultList();
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public List<Driver> getAllDriversWithHealthyTrucks() throws DAOException {
        try {
            TypedQuery<Driver> namedQuery = getManager().createNamedQuery(GET_DRIVERS_HEALTHY_TRUCKS_QUERY,
                    Driver.class);
            return namedQuery.getResultList();
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public Integer getNumberOfDrivers() throws DAOException {
        try {
            TypedQuery<Long> namedQuery = getManager().createNamedQuery(GET_NUMBER_QUERY, Long.class);
            return namedQuery.getSingleResult().intValue();
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public Driver find(User user) throws DAOException {
        try {
            return getManager().find(Driver.class, user.getId());
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public Driver find(Request request) throws DAOException {
        try {
            TypedQuery<Driver> namedQuery = getManager().createNamedQuery(SEARCH_FOR_DRIVER_QUERY, Driver.class);
            namedQuery.setParameter(REQUEST_PARAMETER, request);
            List<Driver> driverList = namedQuery.getResultList();
            if (driverList.isEmpty()) {
                return null;
            } else {
                return driverList.get(0);
            }
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }

    @Override
    public void addDriver(String login, String password, Truck truck) throws DAOException {
        if (login == null || password == null || "".equals(login) || "".equals(password)) {
            throw new DAOException(ExceptionalMessageKey.WRONG_INPUT_PARAMETERS);
        }
        try {
            EntityTransaction transaction = getManager().getTransaction();
            transaction.begin();
            Driver driver = new Driver();
            String encryptedPassword = PasswordEncryptor.encryptPassword(password);
            driver.setPassword(encryptedPassword);
            driver.setLogin(login);
            driver.setTruck(truck);
            getManager().persist(driver);
            transaction.commit();
        } catch (Exception ex) {
            LOG.error(ex);
            throw new DAOException(ExceptionalMessageKey.SQL_ERROR, ex);
        }
    }
}
