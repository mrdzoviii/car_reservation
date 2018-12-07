package ba.telegroup.car_reservation.util;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginInfo {
    @NotNull
    @Size(min=5,max=80,message = "Username length between 5 and 80")
    private String username;
    @NotNull
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}", message = "Password must be minimum 8 characters long. Must contain digits,lower and upper letters and special characters @,#,$,%,^,&,+,=")
    private String password;
    @Size(min=5,max=100,message = "Company name length between 5 and 100")
    private String company;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LoginInfo() {
    }

    public LoginInfo(String username, String password, String company) {
        this.username = username;
        this.password = password;
        this.company = company;
    }
}
