package ba.telegroup.car_reservation.model;

import ba.telegroup.car_reservation.common.interfaces.Deletable;
import ba.telegroup.car_reservation.common.interfaces.HasCompanyId;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.Objects;

@SqlResultSetMapping(
        name = "UserMapping",
        classes = @ConstructorResult(targetClass = User.class,
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
                        @ColumnResult(name = "deleted", type = Byte.class)
                }
        )
)

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements HasCompanyId, Deletable {
    @Positive(message = "Id must be positive integer!")
    private Integer id;
    @Email(message = "Must be well-formed mail!")
    @Size(min = 10,max=100,message = "Email length must be between 10 and 100!")
    private String email;

    @Size(max=80,min = 4,message = "Username length must be between 4 and 100!")
    private String username;

    @Size(max=128,min=128)
    private String password;

    @Size(min=5,max=255,message = "First name length must be between 5 and 255!")
    private String firstName;

    @Size(min=5,max=255,message = "Last name length must be between 5 and 255!")
    private String lastName;

    @PositiveOrZero(message = "Status must be integer!")
    private Integer statusId;

    @PositiveOrZero(message = "Deleted must be integer!")
    private Byte deleted;

    @Positive(message = "MailOptionID must be positive integer!")
    private Integer mailOptionId;

    @Positive(message = "LocationID must be positive integer!")
    private Integer locationId;

    @Positive(message = "CompanyID must be positive integer!")
    private Integer companyId;

    @PositiveOrZero(message = "RoleID must be integer!")
    private Integer roleId;

    private String token;
    private byte[] avatar;

    public User() {
    }

    public User(Integer id, String email,String username, String firstName, String lastName, Integer statusId, Integer companyId, Integer locationId, Integer mailOptionId, byte[] avatar, Integer roleId,Byte deleted) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.statusId = statusId;
        this.deleted = deleted;
        this.mailOptionId = mailOptionId;
        this.locationId = locationId;
        this.companyId = companyId;
        this.roleId = roleId;
        this.avatar = avatar;
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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "status_id")
    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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
    @Column(name = "mail_option_id")
    public Integer getMailOptionId() {
        return mailOptionId;
    }

    public void setMailOptionId(Integer mailOptionId) {
        this.mailOptionId = mailOptionId;
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
    @Column(name = "company_id")
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "role_id")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Basic
    @Column(name = "avatar")
    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
