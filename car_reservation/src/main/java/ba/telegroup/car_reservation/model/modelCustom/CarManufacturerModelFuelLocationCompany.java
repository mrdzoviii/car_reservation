package ba.telegroup.car_reservation.model.modelCustom;

import ba.telegroup.car_reservation.common.interfaces.Deletable;
import ba.telegroup.car_reservation.model.Car;

import javax.persistence.*;
import java.math.BigDecimal;


@SqlResultSetMapping(name = "CarManufacturerModelFuelLocationCompanyMapping",
        classes = @ConstructorResult(targetClass = CarManufacturerModelFuelLocationCompany.class, columns = {
                @ColumnResult(name = "id", type = Integer.class),
                @ColumnResult(name = "model_id", type = Integer.class),
                @ColumnResult(name = "location_id", type = Integer.class),
                @ColumnResult(name = "plate_number", type = String.class),
                @ColumnResult(name = "deleted", type = Byte.class),
                @ColumnResult(name = "engine", type = String.class),
                @ColumnResult(name = "model", type = String.class),
                @ColumnResult(name = "transmission", type = String.class),
                @ColumnResult(name = "year", type = String.class),
                @ColumnResult(name = "image", type = byte[].class),
                @ColumnResult(name = "fuel_name", type = String.class),
                @ColumnResult(name = "fuel_id", type = Integer.class),
                @ColumnResult(name = "manufacturer_id", type = Integer.class),
                @ColumnResult(name = "manufacturer_name", type = String.class),
                @ColumnResult(name = "location_name", type = String.class),
                @ColumnResult(name = "longitude", type = BigDecimal.class),
                @ColumnResult(name = "latitude", type = BigDecimal.class),
                @ColumnResult(name = "company_name", type = String.class),
                @ColumnResult(name = "company_id", type = Integer.class)
        }
        ))


@MappedSuperclass
public class CarManufacturerModelFuelLocationCompany extends Car implements Deletable {

    @Column(name="engine")
    private String engine;
    @Column(name="model")
    private String model;
    @Column(name="transmission")
    private String transmission;
    @Column(name = "year")
    private String year;
    @Column(name="image")
    private byte[] image;
    @Column(name = "fuel_name")
    private String fuelName;
    @Column(name = "fuel_id")
    private Integer fuelId;
    @Column(name = "manufacturer_id")
    private Integer manufacturerId;
    @Column(name="manufacturer_name")
    private String manufacturerName;
    @Column(name = "location_name")
    private String locationName;
    @Column(name="company_name")
    private String companyName;
    @Column(name="company_id")
    private Integer companyId;
    @Column(name="longitude")
    private BigDecimal longitude;
    @Column(name="latitude")
    private BigDecimal latitude;
    public CarManufacturerModelFuelLocationCompany(Integer id, Integer modelId, Integer locationId, String plateNumber, Byte deleted,
                                                  String engine,String model,String transmission,String year,byte[] image,
                                                   String fuelName,Integer fuelId,Integer manufacturerId,String manufacturerName,String locationName,
                                                   BigDecimal longitude,BigDecimal latitude,String companyName,Integer companyId) {
        super(id,modelId,locationId,plateNumber,deleted);
        this.engine = engine;
        this.model = model;
        this.transmission = transmission;
        this.year = year;
        this.image = image;
        this.fuelName = fuelName;
        this.fuelId = fuelId;
        this.manufacturerId = manufacturerId;
        this.manufacturerName = manufacturerName;
        this.locationName = locationName;
        this.companyName = companyName;
        this.companyId = companyId;
        this.longitude=longitude;
        this.latitude=latitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public String getFuelName() {
        return fuelName;
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
    }

    public Integer getFuelId() {
        return fuelId;
    }

    public void setFuelId(Integer fuelId) {
        this.fuelId = fuelId;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

}
