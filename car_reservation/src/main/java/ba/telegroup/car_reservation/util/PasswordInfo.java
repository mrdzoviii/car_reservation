package ba.telegroup.car_reservation.util;

public class PasswordInfo {
    private String oldPassword;
    private String newPassword;
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
