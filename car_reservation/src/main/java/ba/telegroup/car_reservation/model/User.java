package ba.telegroup.car_reservation.model;

import ba.telegroup.car_reservation.common.interfaces.Deletable;
import ba.telegroup.car_reservation.common.interfaces.HasCompanyId;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements HasCompanyId,Deletable {
    private Integer id;
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Byte status;
    private Byte deleted;
    private Integer mailOptionId;
    private Integer locationId;
    private Integer companyId;
    private Integer roleId;
    private String token;
    private Timestamp tokenValidUntil;
    private byte[] avatar;

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
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
    @Column(name = "token_valid_until")
    public Timestamp getTokenValidUntil() {
        return tokenValidUntil;
    }

    public void setTokenValidUntil(Timestamp tokenValidUntil) {
        this.tokenValidUntil = tokenValidUntil;
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
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(status, user.status) &&
                Objects.equals(deleted, user.deleted) &&
                Objects.equals(mailOptionId, user.mailOptionId) &&
                Objects.equals(locationId, user.locationId) &&
                Objects.equals(companyId, user.companyId) &&
                Objects.equals(roleId, user.roleId) &&
                Objects.equals(token, user.token) &&
                Objects.equals(tokenValidUntil, user.tokenValidUntil) &&
                Arrays.equals(avatar, user.avatar);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, email, username, password, firstName, lastName, status, deleted, mailOptionId, locationId, companyId, roleId, token, tokenValidUntil);
        result = 31 * result + Arrays.hashCode(avatar);
        return result;
    }
}
