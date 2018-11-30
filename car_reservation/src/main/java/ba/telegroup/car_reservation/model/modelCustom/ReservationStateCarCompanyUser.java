package ba.telegroup.car_reservation.model.modelCustom;

import ba.telegroup.car_reservation.model.Reservation;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import java.util.Date;

@SqlResultSetMapping(name = "ReservationStateCarCompanyUserMapping",
        classes = @ConstructorResult(targetClass = ReservationStateCarCompanyUser.class,columns = {
            @ColumnResult(name = "id",type = Integer.class),
                @ColumnResult(name="created_time",type=Date.class),
                @ColumnResult(name="start_time",type = Date.class),
                @ColumnResult(name="end_time",type=Date.class),
                @ColumnResult(name = "start_mileage",type=Integer.class),
                @ColumnResult(name = "finish_mileage",type=Integer.class),
                @ColumnResult(name = "direction",type = String.class),
                @ColumnResult(name = "state_id",type=Integer.class),
                @ColumnResult(name="state",type=String.class),
                @ColumnResult(name="user_id",type=Integer.class),
                @ColumnResult(name="deleted",type=Byte.class),
                @ColumnResult(name="car_id",type=Integer.class),
                @ColumnResult(name="username",type=String.class),
                @ColumnResult(name="full_name",type = String.class),
                @ColumnResult(name="car_name",type = String.class),
                @ColumnResult(name="plate_number",type = String.class),
                @ColumnResult(name="engine",type = String.class),
                @ColumnResult(name="image",type=byte[].class),
                @ColumnResult(name="fuel_name",type=String.class),
                @ColumnResult(name="fuel_id",type=Integer.class),
                @ColumnResult(name = "manufacturer_id",type = Integer.class),
                @ColumnResult(name="manufacturer_name",type=String.class),
                @ColumnResult(name="transmission",type = String.class),
                @ColumnResult(name="company_id",type = Integer.class),
                @ColumnResult(name="company_name",type = String.class),
                @ColumnResult(name="year",type = String.class),
                @ColumnResult(name="model",type=String.class)
        }
        ))


@MappedSuperclass
public class ReservationStateCarCompanyUser extends Reservation {

   public ReservationStateCarCompanyUser(Integer id, Date createdTime,Date startTime,Date endTime,Integer startMileage,
                                         Integer finishMileage,String direction,Integer stateId,String state,Integer userId,Byte deleted,
                                         Integer carId,String username,String fullName,String carName,String plateNumber,String engine,byte[] image,
                                         String fuelName,Integer fuelId,Integer manufacturerId,String manufacturerName,
                                         String transmission,Integer companyId,String companyName,String year,String model){
       super(id,createdTime,userId,carId,startMileage,finishMileage,direction,startTime,endTime,stateId,deleted,companyId);
       this.state = state;
       this.username = username;
       this.fullName = fullName;
       this.plateNumber = plateNumber;
       this.engine = engine;
       this.image = image;
       this.fuelName = fuelName;
       this.fuelId = fuelId;
       this.manufacturerId = manufacturerId;
       this.manufacturerName = manufacturerName;
       this.transmission = transmission;
       this.companyName = companyName;
       this.carName=carName;
       this.year=year;
       this.model=model;
   }
    private String model;
    private String year;
    private String state;
    private String username;
    private String fullName;
    private String carName;
    private String plateNumber;
    private String engine;
    private byte[] image;
    private String transmission;
    private String companyName;
    private Integer fuelId;
    private String fuelName;
    private Integer manufacturerId;
    private String manufacturerName;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getFuelId() {
        return fuelId;
    }

    public void setFuelId(Integer fuelId) {
        this.fuelId = fuelId;
    }

    public String getFuelName() {
        return fuelName;
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
