package by.bsu.dektiarev.entity;

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
                query = "SELECT r FROM Request r WHERE r.id NOT IN (SELECT t.request.id FROM Trip t)")
})
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "CARGO_WEIGHT")
    private int cargoWeight;

    @ManyToOne
    @JoinColumn(name = "DEPARTURE_POINT", referencedColumnName = "ID")
    private Station departurePoint;

    @ManyToOne
    @JoinColumn(name = "DESTINATION_POINT", referencedColumnName = "ID")
    private Station destinationPoint;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id &&
                cargoWeight == request.cargoWeight &&
                Objects.equals(departurePoint, request.departurePoint) &&
                Objects.equals(destinationPoint, request.destinationPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cargoWeight, departurePoint, destinationPoint);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", cargoWeight=" + cargoWeight +
                ", departurePoint=" + departurePoint +
                ", destinationPoint=" + destinationPoint +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Station getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(Station departurePoint) {
        this.departurePoint = departurePoint;
    }

    public Station getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(Station destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(int cargoWeight) {
        this.cargoWeight = cargoWeight;
    }
}
