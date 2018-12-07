package ba.telegroup.car_reservation.util;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PasswordInfo {
    @NotNull
    private String oldPassword;

    @NotNull
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}", message = "Password must be minimum 8 characters long. Must contain digits,lower and upper letters and special characters @,#,$,%,^,&,+,=")
    private String newPassword;

    @NotNull
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}", message = "Password must be minimum 8 characters long. Must contain digits,lower and upper letters and special characters @,#,$,%,^,&,+,=")
    private String newPasswordRepeated;

    public String getNewPasswordRepeated() {
        return newPasswordRepeated;
    }

    public void setNewPasswordRepeated(String newPasswordRepeated) {
        this.newPasswordRepeated = newPasswordRepeated;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public PasswordInfo(String oldPassword, String newPassword, String newPasswordRepeated) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordRepeated = newPasswordRepeated;
    }

    public PasswordInfo() {
    }
}
