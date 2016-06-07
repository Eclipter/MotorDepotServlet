package action.controller;

import dao.DAODriver;
import entity.DriverEntity;
import entity.UserEntity;

import java.io.Serializable;

/**
 * Created by USER on 29.05.2016.
 */
public class UserController implements Serializable {

    private static final long serialVersionUID = -3901364261477685125L;

    private UserEntity userEntity;
    private boolean admin;

    private void checkForAdmin() {
        DAODriver daoDriver = new DAODriver();
        DriverEntity driverEntity = daoDriver.searchByUser(userEntity);
        this.admin = driverEntity == null;
    }

    public void reset() {
        userEntity = null;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        checkForAdmin();
    }

    public boolean isAdmin() {
        return admin;
    }
}
