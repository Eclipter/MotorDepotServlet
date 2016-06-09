package entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "trip", schema = "motor_depot")
@NamedQueries({
        @NamedQuery(name = "TripEntity.getAll", query = "SELECT t FROM TripEntity t"),
        @NamedQuery(name = "TripEntity.getByDriverAndRequest", query = "SELECT t FROM  TripEntity t " +
                "WHERE t.requestByRequestId.id = :requestId AND t.driverByDriverUserId.id = :driverId")
})
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IS_COMPLETE")
    private boolean isComplete;

    @OneToOne
    @JoinColumn(name = "REQUEST_ID", referencedColumnName = "ID")
    private RequestEntity requestByRequestId;

    @ManyToOne
    @JoinColumn(name = "DRIVER_USER_ID", referencedColumnName = "USER_ID")
    private DriverEntity driverByDriverUserId;

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
        TripEntity that = (TripEntity) o;
        return isComplete == that.isComplete &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isComplete);
    }

    public RequestEntity getRequestByRequestId() {
        return requestByRequestId;
    }

    public void setRequestByRequestId(RequestEntity applicationByApplicationId) {
        this.requestByRequestId = applicationByApplicationId;
    }


    public DriverEntity getDriverByDriverUserId() {
        return driverByDriverUserId;
    }

    public void setDriverByDriverUserId(DriverEntity driverByDriverUserId) {
        this.driverByDriverUserId = driverByDriverUserId;
    }

    @Override
    public String toString() {
        return "TripEntity{" +
                "id=" + id +
                ", isComplete=" + isComplete +
                ", applicationByApplicationId=" + requestByRequestId +
                ", driverByDriverUserId=" + driverByDriverUserId +
                '}';
    }
}
