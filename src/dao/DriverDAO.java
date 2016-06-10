package dao;

import entity.DriverEntity;
import entity.TruckEntity;
import entity.UserEntity;
import exception.DAOException;
import exception.ExceptionalMessage;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * DAO class that operates on DRIVER table
 * Created by USER on 21.04.2016.
 */
public class DriverDAO extends GenericDAO {

    private static final String GET_ALL_QUERY = "DriverEntity.getAll";
    private static final String GET_DRIVERS_HEALTHY_TRUCKS_QUERY = "DriverEntity.getDriversWithHealthyTrucks";

    /**
     * Gets all drivers that are present in the database
     * @return drivers list
     */
    public List<DriverEntity> getAllDrivers() {
        TypedQuery<DriverEntity> namedQuery = getManager().createNamedQuery(GET_ALL_QUERY, DriverEntity.class);
        return namedQuery.getResultList();
    }

    /**
     * Gets all drivers whose trucks are in a good condition
     * @return drivers list
     */
    public List<DriverEntity> getDriversWithHealthyTrucks() {
        TypedQuery<DriverEntity> namedQuery = getManager().createNamedQuery(GET_DRIVERS_HEALTHY_TRUCKS_QUERY,
                DriverEntity.class);
        return namedQuery.getResultList();
    }

    /**
     * Searches driver by user
     * @param userEntity corresponding user
     * @return driver entity or null if there s no such user or this user is not a driver
     */
    public DriverEntity searchByUser(UserEntity userEntity) {
        return getManager().find(DriverEntity.class, userEntity.getId());
    }

    /**
     * Adds new driver in the database
     * @param userEntity registered user entity
     * @param truckEntity corresponding truck entity
     * @throws DAOException in case of DML error
     */
    public void registerNewDriver(UserEntity userEntity, TruckEntity truckEntity) throws DAOException {
        EntityTransaction transaction = getManager().getTransaction();
        try {
            transaction.begin();
            DriverEntity driverEntity = new DriverEntity();
            driverEntity.setUserId(userEntity.getId());
            driverEntity.setUserByUserId(userEntity);
            driverEntity.setTruckByTruckId(truckEntity);
            getManager().persist(driverEntity);
            transaction.commit();
        } catch (Exception ex) {
            throw new DAOException(ExceptionalMessage.DML_EXCEPTION);
        } finally {
            getManager().clear();
        }
    }
}
