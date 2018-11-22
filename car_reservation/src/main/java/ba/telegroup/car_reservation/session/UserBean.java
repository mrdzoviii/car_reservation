package ba.telegroup.car_reservation.session;



import ba.telegroup.car_reservation.model.modelCustom.UserLocationCompany;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class UserBean {
    private UserLocationCompany user;
    private Boolean loggedIn;

    public UserBean() {
        user = new UserLocationCompany();
        loggedIn = false;
    }

    public UserLocationCompany getUser() {
        return user;
    }

    public void setUser(UserLocationCompany user) {
        this.user = user;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
