package by.bsu.dektiarev.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "trip", schema = "motor_depot")
@NamedQueries({
        @NamedQuery(name = "Trip.getAll", query = "SELECT t FROM Trip t"),
        @NamedQuery(name = "Trip.getByDriverAndRequest", query = "SELECT t FROM  Trip t " +
                "WHERE t.request.id = :requestId AND t.driver.id = :driverId")
})
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IS_COMPLETE")
    private boolean isComplete;

    @OneToOne
    @JoinColumn(name = "REQUEST_ID", referencedColumnName = "ID")
    private Request request;

    @ManyToOne
    @JoinColumn(name = "DRIVER_USER_ID", referencedColumnName = "USER_ID")
    private Driver driver;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip that = (Trip) o;
        return isComplete == that.isComplete &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isComplete);
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }


    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", isComplete=" + isComplete +
                ", request=" + request +
                ", driver=" + driver +
                '}';
    }
}
