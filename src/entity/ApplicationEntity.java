package entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "application", schema = "motor_depot")
@NamedQueries({
        @NamedQuery(name = "ApplicationEntity.getAll", query = "SELECT a FROM ApplicationEntity a"),
        @NamedQuery(name = "ApplicationEntity.searchForDriver",
                query = "SELECT t.driverByDriverUserId FROM TripEntity t WHERE t.applicationByApplicationId = :application")
})
public class ApplicationEntity {

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
        if (!(o instanceof ApplicationEntity)) return false;
        ApplicationEntity that = (ApplicationEntity) o;
        return getId() == that.getId() &&
                getCargoWeight() == that.getCargoWeight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCargoWeight());
    }

    @Override
    public String toString() {
        return "ApplicationEntity{" +
                "id=" + id +
                ", cargoWeight=" + cargoWeight +
                '}';
    }
}
