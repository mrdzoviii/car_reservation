package ba.telegroup.car_reservation.session;


import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.model.modelCustom.UserCompany;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class UserBean {
    private UserCompany user;
    private Boolean loggedIn;

    public UserBean() {
        user = new UserCompany();
        loggedIn = false;
    }

    public UserCompany getUser() {
        return user;
    }

    public void setUser(UserCompany user) {
        this.user = user;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
