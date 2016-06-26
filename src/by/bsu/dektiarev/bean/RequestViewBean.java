package by.bsu.dektiarev.bean;

import by.bsu.dektiarev.entity.Driver;
import by.bsu.dektiarev.entity.Request;

import java.io.Serializable;
import java.util.Objects;

/**
 * Bean that is used to show the information about requests and corresponding drivers in requests.jsp page.
 * Created by USER on 25.04.2016.
 */
public class RequestViewBean implements Serializable {

    private static final long serialVersionUID = 4487366221052810062L;

    private Request request;
    private Driver driver;

    public RequestViewBean(Request request, Driver driver) {
        this.request = request;
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestViewBean that = (RequestViewBean) o;
        return Objects.equals(request, that.request) &&
                Objects.equals(driver, that.driver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, driver);
    }

    public RequestViewBean(Request request) {
        this.request = request;
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
        return "RequestViewBean{" +
                "request=" + request +
                ", driver=" + driver +
                '}';
    }
}
