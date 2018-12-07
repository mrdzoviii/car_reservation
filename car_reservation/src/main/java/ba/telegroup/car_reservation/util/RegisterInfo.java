package ba.telegroup.car_reservation.util;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class RegisterInfo {
    @NotNull
    @Size(max=80,min = 4,message = "Username length must be between 4 and 100!")
    private String username;
    @NotNull
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}", message = "Password must be minimum 8 characters long. Must contain digits,lower and upper letters and special characters @,#,$,%,^,&,+,=")
    private String password;
    @NotNull
    @Size(min=5,max=255,message = "First name length must be between 5 and 255!")
    private String firstName;
    @NotNull
    @Size(min=5,max=255,message = "First name length must be between 5 and 255!")
    private String lastName;
    @NotNull
    @Positive
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
