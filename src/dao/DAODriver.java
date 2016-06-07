package dao;

import entity.DriverEntity;
import entity.TruckEntity;
import entity.UserEntity;
import exception.DAOException;
import exception.ExceptionalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by USER on 21.04.2016.
 */
public class DAODriver extends DAOMotorDepot {

    static Logger logger = LogManager.getLogger();

    public List<DriverEntity> getDriversWithCarOfCapacity(int cargoWeight) {
        TypedQuery<DriverEntity> namedQuery = getManager().createNamedQuery("DriverEntity.getDriverWithCarOfCapacity",
                DriverEntity.class);
        namedQuery.setParameter("cargoWeight", cargoWeight);
        return namedQuery.getResultList();
    }

    public List<DriverEntity> getAllDrivers() {
        TypedQuery<DriverEntity> namedQuery = getManager().createNamedQuery("DriverEntity.getAll", DriverEntity.class);
        return namedQuery.getResultList();
    }

    public List<DriverEntity> getDriversWithHealthyAutos() {
        TypedQuery<DriverEntity> namedQuery = getManager().createNamedQuery("DriverEntity.getDriversWithHealthyAutos", DriverEntity.class);
        return namedQuery.getResultList();
    }

    public DriverEntity getDriverByLogin(String login) {
        TypedQuery<DriverEntity> namedQuery = getManager().createNamedQuery("DriverEntity.getDriverByLogin", DriverEntity.class);
        namedQuery.setParameter("login", login);
        return namedQuery.getResultList().get(0);
    }

    public DriverEntity searchByUser(UserEntity userEntity) {
        return getManager().find(DriverEntity.class, userEntity.getId());
    }

    public void registerNewDriver(UserEntity userEntity, TruckEntity truckEntity) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();

            logger.info("Executing INSERT statement.");
            DriverEntity driverEntity = new DriverEntity();
            driverEntity.setUserId(userEntity.getId());
            driverEntity.setUserByUserId(userEntity);
            driverEntity.setTruckByTruckId(truckEntity);
            getManager().persist(driverEntity);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.SQL_EXCEPTION);
        } finally {
            getManager().clear();
        }
    }
}
