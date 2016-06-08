package entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vladislav on 05.03.2016.
 */
@Entity
@Table(name = "state", schema = "motor_depot")
public class StateEntity {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "STATE_NAME")
    @Enumerated(EnumType.STRING)
    private State stateName;

    @OneToMany(mappedBy = "stateByStateId")
    private List<TruckEntity> trucksById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public State getStateName() {
        return stateName;
    }

    public void setStateName(State stateName) {
        this.stateName = stateName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateEntity that = (StateEntity) o;
        return Objects.equals(id, that.id) &&
                stateName == that.stateName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStateName());
    }

    public List<TruckEntity> getTrucksById() {
        return trucksById;
    }

    public void setTrucksById(List<TruckEntity> trucksById) {
        this.trucksById = trucksById;
    }

    @Override
    public String toString() {
        return "StateEntity{" +
                "id=" + id +
                ", stateName=" + stateName.toString() +
                '}';
    }
}
