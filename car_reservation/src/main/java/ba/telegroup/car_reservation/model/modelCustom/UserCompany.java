package ba.telegroup.car_reservation.model.modelCustom;

import ba.telegroup.car_reservation.model.User;

import javax.persistence.*;

@SqlResultSetMapping(name = "UserCompanyMapping",
classes = @ConstructorResult(
        targetClass = UserCompany.class,
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
                @ColumnResult(name = "company_logo",type=byte[].class)
        }
))

@MappedSuperclass
public class UserCompany extends User {
    @Column(name = "company_name")
    private String companyName;
    @Column(name="company_logo")
    private byte[] companyLogo;

    public UserCompany(Integer id, String email, String username, String firstName, String lastName, Integer statusId, Integer companyId, Integer locationId, Integer mailOptionId, byte[] avatar, Integer roleId, Byte deleted, String companyName, byte[] companyLogo) {
        super(id, email, username, firstName, lastName, statusId, companyId, locationId, mailOptionId, avatar, roleId, deleted);
        this.companyName = companyName;
        this.companyLogo = companyLogo;
    }

    public UserCompany() {
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
}
