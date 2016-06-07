package entity;

/**
 * Bean that is used to show the information about applications and corresponding drivers in applications.jsp page.
 * Created by USER on 25.04.2016.
 */
public class ApplicationViewEntity {

    private ApplicationEntity applicationEntity;
    private DriverEntity driverEntity;

    public ApplicationViewEntity(ApplicationEntity applicationEntity, DriverEntity driverEntity) {
        this.applicationEntity = applicationEntity;
        this.driverEntity = driverEntity;
    }

    public ApplicationViewEntity(ApplicationEntity applicationEntity) {
        this.applicationEntity = applicationEntity;
    }

    public ApplicationEntity getApplicationEntity() {
        return applicationEntity;
    }

    public void setApplicationEntity(ApplicationEntity applicationEntity) {
        this.applicationEntity = applicationEntity;
    }

    public DriverEntity getDriverEntity() {
        return driverEntity;
    }

    public void setDriverEntity(DriverEntity driverEntity) {
        this.driverEntity = driverEntity;
    }

    @Override
    public String toString() {
        return "ApplicationViewEntity{" +
                "applicationEntity=" + applicationEntity +
                ", driverEntity=" + driverEntity +
                '}';
    }
}
