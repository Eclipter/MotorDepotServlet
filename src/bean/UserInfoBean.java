package bean;

import dao.DriverDAO;
import entity.DriverEntity;
import entity.UserEntity;

import java.io.Serializable;

/**
 * Created by USER on 29.05.2016.
 */
public class UserInfoBean implements Serializable {

    private static final long serialVersionUID = -3901364261477685125L;

    private UserEntity userEntity;
    private boolean admin;

    private void checkForAdmin() {
        DriverDAO driverDAO = new DriverDAO();
        DriverEntity driverEntity = driverDAO.searchByUser(userEntity);
        this.admin = driverEntity == null;
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
