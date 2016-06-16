package bean;

import dao.impl.jpa.DriverDAOJPAImpl;
import entity.Driver;
import entity.User;

import java.io.Serializable;

/**
 * Bean that is kept in session attributes.
 * Used to retrieve information about the user that is currently online
 * Created by USER on 29.05.2016.
 */
public class UserInfoBean implements Serializable {

    private static final long serialVersionUID = -3901364261477685125L;

    private User user;
    private boolean admin;

    private void checkForAdmin() {
        DriverDAOJPAImpl driverDAO = new DriverDAOJPAImpl();
        Driver driver = driverDAO.searchByUser(user);
        this.admin = driver == null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        checkForAdmin();
    }

    public boolean isAdmin() {
        return admin;
    }
}
