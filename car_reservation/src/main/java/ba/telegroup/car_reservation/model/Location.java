package ba.telegroup.car_reservation.model;

import ba.telegroup.car_reservation.common.interfaces.Deletable;
import ba.telegroup.car_reservation.common.interfaces.HasCompanyId;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

@SqlResultSetMapping(name = "LocationMapping",
        classes = @ConstructorResult(targetClass = Location.class,columns = {
                @ColumnResult(name = "id",type = Integer.class),
                @ColumnResult(name="name",type=String.class),
                @ColumnResult(name="address",type=String.class),
                @ColumnResult(name="company_id",type = Integer.class),
                @ColumnResult(name="latitude",type = BigDecimal.class),
                @ColumnResult(name="longitude",type=BigDecimal.class),
                @ColumnResult(name="deleted",type=Byte.class)
        }))

@Entity
public class Location implements HasCompanyId, Deletable {
    @Positive(message = "Id must be positive integer!")
    private Integer id;
    @NotNull(message = "Location name must be not null!")
    @NotBlank(message="Location name must be not blank!")
    @Size(max = 50,min = 1,message = "Location name length must be between 1 and 50!")
    private String name;
    @NotNull(message = "Address must be not null!")
    @NotBlank(message="Address must be not blank!")
    private String address;
    @NotNull(message = "Company id must be not null!")
    @Positive(message = "Company id must be positive integer!")
    private Integer companyId;
    @NotNull(message = "Latitude must be not null!")
    @Positive(message = "Latitude must be positive number!")
    private BigDecimal latitude;
    @NotNull(message = "Longitude must be not null!")
    @Positive(message = "Longitude must be positive number!")
    private BigDecimal longitude;
    @NotNull(message = "Deleted must be not null!")
    @PositiveOrZero(message = "Deleted must be positive integer or zero!")
    private Byte deleted;

    public Location(Integer id, String name, String address,  Integer companyId,BigDecimal latitude, BigDecimal longitude, Byte deleted) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.companyId = companyId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deleted = deleted;
    }

    public Location() {
    }

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "company_id")
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "latitude")
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "deleted")
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
