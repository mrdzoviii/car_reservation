package ba.telegroup.car_reservation.model.modelCustom;

import ba.telegroup.car_reservation.model.User;

import javax.persistence.*;
import java.math.BigDecimal;

@SqlResultSetMapping(name = "UserLocationCompanyMapping",
classes = @ConstructorResult(
        targetClass = UserLocationCompany.class,
        columns = {
                @ColumnResult(name = "id", type = Integer.class),
                @ColumnResult(name = "email", type = String.class),
                @ColumnResult(name = "username", type = String.class),
                @ColumnResult(name = "first_name", type = String.class),
                @ColumnResult(name = "last_name", type = String.class),
                @ColumnResult(name = "status_id", type = Integer.class),
                @ColumnResult(name = "company_id", type = Integer.class),
                @ColumnResult(name = "location_id", type = Integer.class),
                @ColumnResult(name = "mail_option_id", type = Integer.class),
                @ColumnResult(name = "avatar", type = byte[].class),
                @ColumnResult(name = "role_id", type = Integer.class),
                @ColumnResult(name = "deleted", type = Byte.class),
                @ColumnResult(name = "company_name",type=String.class),
                @ColumnResult(name = "company_logo",type=byte[].class),
                @ColumnResult(name="location_name",type=String.class),
                @ColumnResult(name="location_address",type = String.class),
                @ColumnResult(name="location_latitude",type= BigDecimal.class),
                @ColumnResult(name="location_longitude",type=BigDecimal.class),
                @ColumnResult(name="full_name",type=String.class)

        }
))

@MappedSuperclass
public class UserLocationCompany extends User {
    @Column(name = "company_name")
    private String companyName;
    @Column(name="company_logo")
    private byte[] companyLogo;
    @Column(name = "location_name")
    private String locationName;
    @Column(name="location_address")
    private String locationAddress;
    @Column(name="location_latitude")
    private BigDecimal locationLatitude;
    @Column(name="location_longitude")
    private BigDecimal locationLongitude;
    @Column(name="full_name")
    private String fullName;

    public UserLocationCompany(Integer id, String email, String username, String firstName, String lastName, Integer statusId,
                               Integer companyId, Integer locationId, Integer mailOptionId, byte[] avatar, Integer roleId,
                               Byte deleted, String companyName, byte[] companyLogo,String locationName,String locationAddress,
                               BigDecimal locationLatitude,BigDecimal locationLongitude,String fullName) {
        super(id, email, username, firstName, lastName, statusId, companyId, locationId, mailOptionId, avatar, roleId, deleted);
        this.companyName = companyName;
        this.companyLogo = companyLogo;
        this.locationName=locationName;
        this.locationAddress=locationAddress;
        this.locationLatitude=locationLatitude;
        this.locationLongitude=locationLongitude;
        this.fullName=fullName;
    }

    public UserLocationCompany() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public byte[] getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public BigDecimal getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(BigDecimal locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public BigDecimal getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(BigDecimal locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
