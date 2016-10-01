package by.bsu.dektiarev.bean;

import by.bsu.dektiarev.entity.Driver;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by USER on 01.10.2016.
 */
public class DriverViewBean implements Serializable {

    private static final long serialVersionUID = -2229055078066334412L;

    private Driver driver;
    private Integer completedTripsCount;
    private Integer tripsCount;

    public DriverViewBean(Driver driver, Integer completedTripsCount, Integer tripsCount) {
        this.driver = driver;
        this.completedTripsCount = completedTripsCount;
        this.tripsCount = tripsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverViewBean that = (DriverViewBean) o;
        return Objects.equals(driver, that.driver) &&
                Objects.equals(completedTripsCount, that.completedTripsCount) &&
                Objects.equals(tripsCount, that.tripsCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver, completedTripsCount, tripsCount);
    }

    @Override
    public String toString() {
        return "DriverViewBean{" +
                "driver=" + driver +
                ", completedTripsCount=" + completedTripsCount +
                ", tripsCount=" + tripsCount +
                '}';
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Integer getCompletedTripsCount() {
        return completedTripsCount;
    }

    public void setCompletedTripsCount(Integer completedTripsCount) {
        this.completedTripsCount = completedTripsCount;
    }

    public Integer getTripsCount() {
        return tripsCount;
    }

    public void setTripsCount(Integer tripsCount) {
        this.tripsCount = tripsCount;
    }
}
