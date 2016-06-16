package entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "trip", schema = "motor_depot")
@NamedQueries({
        @NamedQuery(name = "Trip.getAll", query = "SELECT t FROM Trip t"),
        @NamedQuery(name = "Trip.getByDriverAndRequest", query = "SELECT t FROM  Trip t " +
                "WHERE t.requestByRequestId.id = :requestId AND t.driverByDriverUserId.id = :driverId")
})
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IS_COMPLETE")
    private boolean isComplete;

    @OneToOne
    @JoinColumn(name = "REQUEST_ID", referencedColumnName = "ID")
    private Request requestByRequestId;

    @ManyToOne
    @JoinColumn(name = "DRIVER_USER_ID", referencedColumnName = "USER_ID")
    private Driver driverByDriverUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip that = (Trip) o;
        return isComplete == that.isComplete &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isComplete);
    }

    public Request getRequestByRequestId() {
        return requestByRequestId;
    }

    public void setRequestByRequestId(Request applicationByApplicationId) {
        this.requestByRequestId = applicationByApplicationId;
    }


    public Driver getDriverByDriverUserId() {
        return driverByDriverUserId;
    }

    public void setDriverByDriverUserId(Driver driverByDriverUserId) {
        this.driverByDriverUserId = driverByDriverUserId;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", isComplete=" + isComplete +
                ", applicationByApplicationId=" + requestByRequestId +
                ", driverByDriverUserId=" + driverByDriverUserId +
                '}';
    }
}
