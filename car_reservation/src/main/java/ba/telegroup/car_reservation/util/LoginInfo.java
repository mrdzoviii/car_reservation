package ba.telegroup.car_reservation.util;

public class LoginInfo {
    private String username;
    private String password;
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
