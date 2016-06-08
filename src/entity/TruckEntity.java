package entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "truck", schema = "motor_depot")
@NamedQueries({
        @NamedQuery(name = "TruckEntity.getAll", query = "SELECT a FROM TruckEntity a"),
        @NamedQuery(name = "TruckEntity.getByDriver", query = "SELECT a FROM TruckEntity a WHERE a.driverById = :driver")
})
public class TruckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CAPACITY")
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "STATE_ID", referencedColumnName = "ID", nullable = false)
    private StateEntity stateByStateId;

    @OneToOne(mappedBy = "truckByTruckId")
    private DriverEntity driverById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TruckEntity)) return false;
        TruckEntity that = (TruckEntity) o;
        return getId() == that.getId() &&
                getCapacity() == that.getCapacity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCapacity());
    }

    public StateEntity getStateByStateId() {
        return stateByStateId;
    }

    public void setStateByStateId(StateEntity stateByStateId) {
        this.stateByStateId = stateByStateId;
    }

    public DriverEntity getDriverById() {
        return driverById;
    }

    public void setDriverById(DriverEntity driverById) {
        this.driverById = driverById;
    }

    @Override
    public String toString() {
        return "TruckEntity{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", stateByStateId=" + stateByStateId +
                '}';
    }
}
