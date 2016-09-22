package by.bsu.dektiarev.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "truck", schema = "motor_depot")
@NamedQueries({
        @NamedQuery(name = "Truck.getAll", query = "SELECT a FROM Truck a"),
        @NamedQuery(name = "Truck.getNumber", query = "SELECT COUNT(a) FROM Truck a")
})
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NUMBER")
    private String number;

    @Column(name = "CAPACITY")
    private Double capacity;

    @ManyToOne
    @JoinColumn(name = "STATE_ID", referencedColumnName = "ID", nullable = false)
    private TruckStateDTO state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return Objects.equals(id, truck.id) &&
                Objects.equals(number, truck.number) &&
                Objects.equals(capacity, truck.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, capacity);
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", capacity=" + capacity +
                ", state=" + state +
                '}';
    }

    public TruckStateDTO getState() {
        return state;
    }

    public void setState(TruckStateDTO state) {
        this.state = state;
    }

}
