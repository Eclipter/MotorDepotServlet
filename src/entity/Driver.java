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
        @NamedQuery(name = "Driver.getDriversWithHealthyTrucks", query = "SELECT d FROM Driver d where" +
                " d.truckByTruckId IN (SELECT s.trucksById FROM TruckStateDTO s WHERE s.id = 1)"),
        @NamedQuery(name = "Driver.getDriverByLogin", query = "SELECT d FROM Driver d WHERE d.userByUserId =" +
                " (SELECT u FROM User u WHERE u.login = :login)"),
        @NamedQuery(name = "Driver.searchByRequest",
                query = "SELECT t.driverByDriverUserId FROM Trip t WHERE t.requestByRequestId = :request")
})
public class Driver implements Serializable {

    private static final long serialVersionUID = 2825686838429055943L;

    @OneToOne
    @JoinColumn(name = "TRUCK_ID", referencedColumnName = "ID")
    private Truck truckByTruckId;

    @Id
    @Column(name = "USER_ID")
    private Integer userId;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User userByUserId;

    @OneToMany(mappedBy = "driverByDriverUserId")
    private List<Trip> tripsByUserId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver that = (Driver) o;
        return Objects.equals(truckByTruckId, that.truckByTruckId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTruckByTruckId(), getUserByUserId());
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Truck getTruckByTruckId() {
        return truckByTruckId;
    }

    public void setTruckByTruckId(Truck truckByTruckId) {
        this.truckByTruckId = truckByTruckId;
    }

    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    public List<Trip> getTripsByUserId() {
        return tripsByUserId;
    }

    public void setTripsByUserId(List<Trip> tripsByUserId) {
        this.tripsByUserId = tripsByUserId;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "truckByTruckId=" + truckByTruckId +
                ", userByUserId=" + userByUserId +
                '}';
    }
}
