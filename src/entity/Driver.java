package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "driver", schema = "motor_depot")
@NamedQueries({
        @NamedQuery(name = "Driver.getAll", query = "SELECT d FROM Driver d"),
        @NamedQuery(name = "Driver.getDriversWithHealthyTrucks", query = "SELECT d FROM Driver d WHERE d.truck IN " +
                "(SELECT t FROM Truck t WHERE t.state = (SELECT s FROM TruckStateDTO s WHERE s.id = 1))"),
        @NamedQuery(name = "Driver.getDriverByLogin", query = "SELECT d FROM Driver d WHERE d.user =" +
                " (SELECT u FROM User u WHERE u.login = :login)"),
        @NamedQuery(name = "Driver.searchByRequest",
                query = "SELECT t.driver FROM Trip t WHERE t.request = :request")
})
public class Driver implements Serializable {

    private static final long serialVersionUID = 2825686838429055943L;

    @OneToOne
    @JoinColumn(name = "TRUCK_ID", referencedColumnName = "ID")
    private Truck truck;

    @Id
    @Column(name = "USER_ID")
    private Integer userId;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @OneToMany(mappedBy = "driver")
    private List<Trip> tripList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver that = (Driver) o;
        return Objects.equals(truck, that.truck) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTruck(), getUser());
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public void setTripList(List<Trip> tripList) {
        this.tripList = tripList;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "truck=" + truck +
                ", user=" + user +
                '}';
    }
}
