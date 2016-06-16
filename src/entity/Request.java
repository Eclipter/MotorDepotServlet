package entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "request", schema = "motor_depot")
@NamedQueries({
        @NamedQuery(name = "Request.getAll", query = "SELECT r FROM Request r"),
        @NamedQuery(name = "Request.getUnassigned",
                query = "SELECT r FROM Request r WHERE r.id NOT IN (SELECT t.requestByRequestId.id FROM Trip t)")
})
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "CARGO_WEIGHT")
    private int cargoWeight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(int cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request that = (Request) o;
        return id == that.id &&
                cargoWeight == that.cargoWeight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCargoWeight());
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", cargoWeight=" + cargoWeight +
                '}';
    }
}
