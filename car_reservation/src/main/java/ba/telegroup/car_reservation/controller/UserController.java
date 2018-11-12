package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
@Scope("request")
public class UserController extends GenericHasCompanyIdAndDeletableController<User,Integer> {
    private UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository){
        super(userRepository);
        this.userRepository=userRepository;
    }
}
