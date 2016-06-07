package dao;

import entity.ApplicationEntity;
import entity.DriverEntity;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 12.03.2016.
 */
public class DAOApplication extends DAOMotorDepot {

    private static final Logger logger = LogManager.getLogger();

    public List<ApplicationEntity> getAllApplications() {
        TypedQuery<ApplicationEntity> namedQuery = getManager().createNamedQuery("ApplicationEntity.getAll", ApplicationEntity.class);
        return namedQuery.getResultList();
    }

    public List<ApplicationEntity> getUnsetApplications() {
        TypedQuery<ApplicationEntity> namedQuery = getManager().createNamedQuery("ApplicationEntity.getAll", ApplicationEntity.class);
        List<ApplicationEntity> applicationEntityList = namedQuery.getResultList();
        List<ApplicationEntity> unsetApplications = new ArrayList<>();
        for(ApplicationEntity applicationEntity : applicationEntityList) {
            List<DriverEntity> driverEntityList = searchForDriverCompletingApplication(applicationEntity);
            if(driverEntityList.isEmpty()) {
                unsetApplications.add(applicationEntity);
            }
        }
        return unsetApplications;
    }

    public List<DriverEntity> searchForDriverCompletingApplication(ApplicationEntity applicationEntity) {
        TypedQuery<DriverEntity> namedQuery = getManager().createNamedQuery("ApplicationEntity.searchForDriver", DriverEntity.class);
        namedQuery.setParameter("application", applicationEntity);
        return namedQuery.getResultList();
    }

    public void registerNewApplication(int cargoWeight) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();

            logger.info("Executing INSERT statement.");
            ApplicationEntity applicationEntity = new ApplicationEntity();
            applicationEntity.setCargoWeight(cargoWeight);
            getManager().persist(applicationEntity);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.SQL_EXCEPTION);
        } finally {
            getManager().clear();
        }
    }
}
