package ba.telegroup.car_reservation.model.modelCustom;

import ba.telegroup.car_reservation.common.interfaces.Deletable;
import ba.telegroup.car_reservation.model.Car;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @NotNull(message = "Engine must  be not null!")
    @Size(max = 100,min=2,message = "Engine length must be between 2 and 100!")
    private String engine;

    @Column(name="model")
    @NotNull(message = "Model must be not null!")
    @Size(max = 100,min=2,message = "Model length must be between 2 and 100!")
    private String model;

    @Column(name="transmission")
    @NotNull(message = "Transmission must be not null!")
    @Size(max = 100,min=2,message = "Transmission length must be between 2 and 100!")
    private String transmission;

    @Column(name = "year")
    @NotNull(message = "Year must be not null!")
    @Pattern(regexp = "\\d\\d\\d\\d",message = "Year must be in format 0000!" )
    private String year;

    @NotNull(message = "Image must be not null!")
    @Size(min=1,message = "Image size cannot be zero!")
    @Column(name="image")
    private byte[] image;

    @Column(name = "fuel_name")
    private String fuelName;

    @Column(name = "fuel_id")
    @NotNull(message = "Fuel id must be not null!")
    @Positive(message = "Fuel id must be positive integer!")
    private Integer fuelId;

    @Column(name = "manufacturer_id")
    @Positive(message = "Manufacturer id must be positive integer")
    private Integer manufacturerId;

    @Column(name="manufacturer_name")
    @NotNull(message = "Manufacturer must be not null!")
    @Size(max = 100,min=2,message = "Manufacturer name length must be between 2 and 100!")
    private String manufacturerName;

    @Column(name = "location_name")
    @NotNull(message = "Location name must be not null!")
    @Size(max = 100,min=2,message = "Location name length must be between 2 and 100!")
    private String locationName;

    @NotNull(message = "Company name must be not null!")
    @Size(max = 100,min=2,message = "Company name length must be between 2 and 100!")
    @Column(name="company_name")
    private String companyName;

    @NotNull(message = "Company id must be not null!")
    @Positive(message = "Company id must be positive integer!")
    @Column(name="company_id")
    private Integer companyId;

    @NotNull(message = "Longitude must be not null!")
    @Positive(message = "Longitude must be positive number")
    @Column(name="longitude")
    private BigDecimal longitude;

    @NotNull(message = "Latitude must be not null!")
    @Positive(message = "Latitude must be positive number")
    @Column(name="latitude")
    private BigDecimal latitude;

    public CarManufacturerModelFuelLocationCompany(Integer id, Integer modelId, Integer locationId, String plateNumber, Byte deleted,
                                                  String engine,String model,String transmission,String year,byte[] image,
                                                   String fuelName,Integer fuelId,Integer manufacturerId,String manufacturerName,String locationName,
                                                   BigDecimal longitude,BigDecimal latitude,String companyName,Integer companyId) {
        super(id,modelId,locationId,plateNumber,deleted,companyId);
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
