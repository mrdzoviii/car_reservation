package ba.telegroup.car_reservation.model.modelCustom;

import ba.telegroup.car_reservation.model.Reservation;


import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;

@SqlResultSetMapping(name = "ReservationStateCarUserCompanyLocationMapping",classes =
@ConstructorResult(targetClass = ReservationStateCarUserCompanyLocation.class,columns = {
        @ColumnResult(name = "id",type = Integer.class),
        @ColumnResult(name="car_id",type=Integer.class),
        @ColumnResult(name="company_id",type=Integer.class),
        @ColumnResult(name="deleted",type=Byte.class),
        @ColumnResult(name="user_id",type=Integer.class),
        @ColumnResult(name="direction",type=String.class),
        @ColumnResult(name="state_id",type = Integer.class),
        @ColumnResult(name="state",type=String.class),
        @ColumnResult(name="model",type=String.class),
        @ColumnResult(name="location_id",type = Integer.class),
        @ColumnResult(name="location_name",type=String.class),
        @ColumnResult(name="username",type = String.class),
        @ColumnResult(name="full_name",type=String.class),
        @ColumnResult(name="period",type=String.class)
}))

@MappedSuperclass
public class ReservationStateCarUserCompanyLocation extends Reservation {
    private String state;
    private String model;
    private Integer locationId;
    private String locationName;
    private String username;
    private String fullName;
    private String period;

    public ReservationStateCarUserCompanyLocation(Integer id,Integer carId,Integer companyId,Byte deleted,Integer userId,String direction,Integer stateId,String state,String model,
                                                  Integer locationId,String locationName,String username,String fullName,String period){
        super(id,userId,carId,direction,stateId,deleted,companyId);
        this.state = state;
        this.model = model;
        this.locationId = locationId;
        this.locationName = locationName;
        this.username = username;
        this.fullName = fullName;
        this.period=period;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
