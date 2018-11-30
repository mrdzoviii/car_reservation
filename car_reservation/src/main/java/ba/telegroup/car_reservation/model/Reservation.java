package ba.telegroup.car_reservation.model;

import ba.telegroup.car_reservation.common.interfaces.Deletable;
import ba.telegroup.car_reservation.common.interfaces.HasCompanyId;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Reservation implements Deletable, HasCompanyId {
    private Integer id;
    private Timestamp createdTime;
    private Integer userId;
    private Integer carId;
    private Integer startMileage;
    private Integer finishMileage;
    private String direction;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer stateId;
    private Byte deleted;
    private Integer companyId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "created_time")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "car_id")
    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    @Basic
    @Column(name = "start_mileage")
    public Integer getStartMileage() {
        return startMileage;
    }

    public void setStartMileage(Integer startMileage) {
        this.startMileage = startMileage;
    }

    @Basic
    @Column(name = "finish_mileage")
    public Integer getFinishMileage() {
        return finishMileage;
    }

    public void setFinishMileage(Integer finishMileage) {
        this.finishMileage = finishMileage;
    }

    @Basic
    @Column(name = "direction")
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Basic
    @Column(name = "start_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "state_id")
    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    @Basic
    @Column(name = "deleted")
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name="company_id")
    public Integer getCompanyId(){
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Reservation() {
    }

    public Reservation(Integer id, Date createdTime, Integer userId, Integer carId, Integer startMileage, Integer finishMileage, String direction, Date startTime, Date endTime, Integer stateId, Byte deleted, Integer companyId) {
        this.id = id;
        this.createdTime = new Timestamp(createdTime.getTime());
        this.userId = userId;
        this.carId = carId;
        this.startMileage = startMileage;
        this.finishMileage = finishMileage;
        this.direction = direction;
        this.startTime = new Timestamp(startTime.getTime());
        this.endTime = new Timestamp(endTime.getTime());
        this.stateId = stateId;
        this.deleted = deleted;
        this.companyId = companyId;
    }

    public Reservation(Integer id, Integer userId, Integer carId, String direction, Integer stateId, Byte deleted, Integer companyId) {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.direction = direction;
        this.stateId = stateId;
        this.deleted = deleted;
        this.companyId = companyId;
    }
}
