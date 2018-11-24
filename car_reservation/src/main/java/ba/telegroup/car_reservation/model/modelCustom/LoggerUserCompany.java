package ba.telegroup.car_reservation.model.modelCustom;

import ba.telegroup.car_reservation.model.Logger;

import javax.persistence.*;
import java.util.Date;

@SqlResultSetMapping(name = "LoggerUserCompanyMapping",classes = @ConstructorResult(
        targetClass = LoggerUserCompany.class,
        columns = {
                @ColumnResult(name ="id"),
                @ColumnResult(name="action_type"),
                @ColumnResult(name="action_details"),
                @ColumnResult(name="table_name"),
                @ColumnResult(name="created",type = Date.class),
                @ColumnResult(name="user_id"),
                @ColumnResult(name="atomic"),
                @ColumnResult(name="company_id"),
                @ColumnResult(name="user_username"),
                @ColumnResult(name="company_name"),
                @ColumnResult(name="user_role")
        }
))


@MappedSuperclass
public class LoggerUserCompany extends Logger {
    @Column(name = "user_username")
    private String username;
    @Column(name = "company_name")
    private String companyName;
    @Column(name="user_role")
    private Integer userRole;

    public LoggerUserCompany(Integer id,String actionType,String actionDetails,String tableName,Date created,Integer userId,Byte atomic,Integer companyId,String username,String companyName,Integer userRole) {
        super(id, actionType, actionDetails,tableName,created,atomic,userId,companyId);
        this.username=username;
        this.companyName=companyName;
        this.userRole=userRole;
    }

    public LoggerUserCompany() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }
}
