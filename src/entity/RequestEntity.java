package entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "request", schema = "motor_depot")
@NamedQueries({
        @NamedQuery(name = "RequestEntity.getAll", query = "SELECT a FROM RequestEntity a"),
        @NamedQuery(name = "RequestEntity.searchForDriver",
                query = "SELECT t.driverByDriverUserId FROM TripEntity t WHERE t.requestByRequestId = :request")
})
public class RequestEntity {

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
        RequestEntity that = (RequestEntity) o;
        return id == that.id &&
                cargoWeight == that.cargoWeight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCargoWeight());
    }

    @Override
    public String toString() {
        return "RequestEntity{" +
                "id=" + id +
                ", cargoWeight=" + cargoWeight +
                '}';
    }
}
