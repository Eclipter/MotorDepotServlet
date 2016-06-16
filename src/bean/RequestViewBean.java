package bean;

import entity.Driver;
import entity.Request;

import java.io.Serializable;

/**
 * Bean that is used to show the information about requests and corresponding drivers in requests.jsp page.
 * Created by USER on 25.04.2016.
 */
public class RequestViewBean implements Serializable {

    private Request request;
    private Driver driver;

    public RequestViewBean(Request request, Driver driver) {
        this.request = request;
        this.driver = driver;
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
