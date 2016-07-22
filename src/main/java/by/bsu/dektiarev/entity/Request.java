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
    private Double cargoWeight;

    @ManyToOne
    @JoinColumn(name = "DEPARTURE_STATION_ID", referencedColumnName = "ID")
    private Station departureStation;

    @ManyToOne
    @JoinColumn(name = "DESTINATION_STATION_ID", referencedColumnName = "ID")
    private Station destinationStation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id &&
                Objects.equals(cargoWeight, request.cargoWeight) &&
                Objects.equals(departureStation, request.departureStation) &&
                Objects.equals(destinationStation, request.destinationStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cargoWeight, departureStation, destinationStation);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", cargoWeight=" + cargoWeight +
                ", departureStation=" + departureStation +
                ", destinationStation=" + destinationStation +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Station departurePoint) {
        this.departureStation = departurePoint;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(Station destinationPoint) {
        this.destinationStation = destinationPoint;
    }

    public Double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(Double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }
}
