package entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "trip", schema = "mydb")
@NamedQuery(name = "TripEntity.getAll", query = "SELECT t FROM TripEntity t")
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IS_COMPLETE")
    private boolean isComplete;

    @OneToOne
    @JoinColumn(name = "APPLICATION_ID", referencedColumnName = "ID")
    private ApplicationEntity applicationByApplicationId;

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
        if (!(o instanceof TripEntity)) return false;
        TripEntity that = (TripEntity) o;
        return getId() == that.getId() &&
                isComplete == that.isComplete;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isComplete);
    }

    public ApplicationEntity getApplicationByApplicationId() {
        return applicationByApplicationId;
    }

    public void setApplicationByApplicationId(ApplicationEntity applicationByApplicationId) {
        this.applicationByApplicationId = applicationByApplicationId;
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
                ", applicationByApplicationId=" + applicationByApplicationId +
                ", driverByDriverUserId=" + driverByDriverUserId +
                '}';
    }
}
