package entity;

import entity.util.TruckState;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "truck_state", schema = "motor_depot")
public class TruckStateDTO {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "STATE_NAME")
    @Enumerated(EnumType.STRING)
    private TruckState truckStateName;

    @OneToMany(mappedBy = "stateByStateId")
    private List<Truck> trucksById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TruckState getTruckStateName() {
        return truckStateName;
    }

    public void setTruckStateName(TruckState truckStateName) {
        this.truckStateName = truckStateName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckStateDTO that = (TruckStateDTO) o;
        return Objects.equals(id, that.id) &&
                truckStateName == that.truckStateName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTruckStateName());
    }

    public List<Truck> getTrucksById() {
        return trucksById;
    }

    public void setTrucksById(List<Truck> trucksById) {
        this.trucksById = trucksById;
    }

    @Override
    public String toString() {
        return "TruckStateDTO{" +
                "id=" + id +
                ", truckStateName=" + truckStateName.toString() +
                '}';
    }
}
