package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "driver", schema = "mydb")
@NamedQueries({
        @NamedQuery(name = "DriverEntity.getAll", query = "SELECT d FROM DriverEntity d"),
        @NamedQuery(name = "DriverEntity.getDriverWithCarOfCapacity", query = "SELECT d FROM DriverEntity d where " +
                "d.truckByTruckId IN (SELECT a FROM TruckEntity a WHERE a.capacity > :cargoWeight AND a.stateByStateId = " +
                "(SELECT s FROM StateEntity s WHERE s.id = 1))"),
        @NamedQuery(name = "DriverEntity.getDriversWithHealthyAutos", query = "SELECT d FROM DriverEntity d where" +
                " d.truckByTruckId IN (SELECT a FROM TruckEntity a WHERE a.stateByStateId = " +
                "(SELECT s FROM StateEntity s WHERE s.id = 1))"),
        @NamedQuery(name = "DriverEntity.getDriverByLogin", query = "SELECT d FROM DriverEntity d WHERE d.userByUserId =" +
                " (SELECT u FROM UserEntity u WHERE u.login = :login)")
})
public class DriverEntity implements Serializable {

    private static final long serialVersionUID = 2825686838429055943L;

    @OneToOne
    @JoinColumn(name = "TRUCK_ID", referencedColumnName = "ID")
    private TruckEntity truckByTruckId;

    @Id
    @Column(name = "USER_ID")
    private Integer userId;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private UserEntity userByUserId;

    @OneToMany(mappedBy = "driverByDriverUserId")
    private List<TripEntity> tripsByUserId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverEntity)) return false;
        DriverEntity that = (DriverEntity) o;
        return Objects.equals(getTruckByTruckId(), that.getTruckByTruckId()) &&
                Objects.equals(getUserByUserId(), that.getUserByUserId());
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

    public TruckEntity getTruckByTruckId() {
        return truckByTruckId;
    }

    public void setTruckByTruckId(TruckEntity truckByTruckId) {
        this.truckByTruckId = truckByTruckId;
    }

    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    public List<TripEntity> getTripsByUserId() {
        return tripsByUserId;
    }

    public void setTripsByUserId(List<TripEntity> tripsByUserId) {
        this.tripsByUserId = tripsByUserId;
    }

    @Override
    public String toString() {
        return "DriverEntity{" +
                "truckByBruckId=" + truckByTruckId +
                ", userByUserId=" + userByUserId +
                '}';
    }
}
