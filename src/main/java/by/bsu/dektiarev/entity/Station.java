package by.bsu.dektiarev.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by USER on 21.07.2016.
 */

@Entity
@Table(name = "station", schema = "motor_depot")
@NamedQueries({
        @NamedQuery(name = "Station.getAll", query = "SELECT s FROM Station s")
})
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(id, station.id) &&
                Objects.equals(name, station.name) &&
                Objects.equals(address, station.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
