package entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "truck", schema = "motor_depot")
@NamedQueries({
        @NamedQuery(name = "Truck.getAll", query = "SELECT a FROM Truck a")
})
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CAPACITY")
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "STATE_ID", referencedColumnName = "ID", nullable = false)
    private TruckStateDTO stateByStateId;

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
        if (o == null || getClass() != o.getClass()) return false;
        Truck that = (Truck) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(capacity, that.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCapacity());
    }

    public TruckStateDTO getStateByStateId() {
        return stateByStateId;
    }

    public void setStateByStateId(TruckStateDTO stateByStateId) {
        this.stateByStateId = stateByStateId;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", stateByStateId=" + stateByStateId +
                '}';
    }
}
