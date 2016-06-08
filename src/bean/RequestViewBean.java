package bean;

import entity.RequestEntity;
import entity.DriverEntity;

/**
 * Bean that is used to show the information about requests and corresponding drivers in requests.jsp page.
 * Created by USER on 25.04.2016.
 */
public class RequestViewBean {

    private RequestEntity requestEntity;
    private DriverEntity driverEntity;

    public RequestViewBean(RequestEntity requestEntity, DriverEntity driverEntity) {
        this.requestEntity = requestEntity;
        this.driverEntity = driverEntity;
    }

    public RequestViewBean(RequestEntity requestEntity) {
        this.requestEntity = requestEntity;
    }

    public RequestEntity getRequestEntity() {
        return requestEntity;
    }

    public void setRequestEntity(RequestEntity requestEntity) {
        this.requestEntity = requestEntity;
    }

    public DriverEntity getDriverEntity() {
        return driverEntity;
    }

    public void setDriverEntity(DriverEntity driverEntity) {
        this.driverEntity = driverEntity;
    }

    @Override
    public String toString() {
        return "RequestViewBean{" +
                "requestEntity=" + requestEntity +
                ", driverEntity=" + driverEntity +
                '}';
    }
}
