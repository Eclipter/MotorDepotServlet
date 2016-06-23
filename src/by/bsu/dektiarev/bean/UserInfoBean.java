package by.bsu.dektiarev.bean;

import by.bsu.dektiarev.entity.User;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
