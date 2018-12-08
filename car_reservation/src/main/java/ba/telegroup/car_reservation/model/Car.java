package ba.telegroup.car_reservation.model;

import ba.telegroup.car_reservation.common.interfaces.Deletable;
import ba.telegroup.car_reservation.common.interfaces.HasCompanyId;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Car implements Deletable, HasCompanyId {
    private Integer id;
    @Positive(message = "Model id must be positive integer!")
    private Integer modelId;
    @NotNull(message = "Location id must be not null!")
    @Positive(message = "Location id must be positive integer!")
    private Integer locationId;
    @NotNull
    @NotBlank
    @Pattern(regexp = "([AEJKMOT]\\d\\d-[AEJKMOT]-\\d\\d\\d)|(\\d\\d\\d-[AEJKMOT]-\\d\\d\\d)",message = "Plate number must be in X00-X-000 or 000-X-000 format where X is any letter from group(A,E,J,K,M,O,T) and 0 is any digit!")
    private String plateNumber;
    @NotNull(message = "Deleted must be not null!")
    @PositiveOrZero(message = "Deleted must be 0 or positive integer!")
    private Byte deleted;
    @NotNull(message = "Company id must be not null!")
    @PositiveOrZero(message = "Company id must be positive integer!")
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
    @Column(name = "model_id")
    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "location_id")
    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    @Basic
    @Column(name = "plate_number")
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
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
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id);
    }

    public Car(Integer id, Integer modelId, Integer locationId, String plateNumber, Byte deleted,Integer companyId) {
        this.id = id;
        this.modelId = modelId;
        this.locationId = locationId;
        this.plateNumber = plateNumber;
        this.deleted = deleted;
        this.companyId=companyId;
    }

    public Car() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
