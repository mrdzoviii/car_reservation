package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdController;
import ba.telegroup.car_reservation.model.LoginBean;
import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.repository.UserRepository;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequestMapping("/api/user")
@RestController
@Scope("request")
public class UserController extends GenericHasCompanyIdController<User,Integer> {
    @Value(value = "${forbidden.login}")
    private String forbiddenLogin;
    private UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository){
        super(userRepository);
        this.userRepository=userRepository;
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public User login(@RequestBody LoginBean bean) throws ForbiddenException {
        User loggedUser= userRepository.login(bean.getUsername(),bean.getPassword(),bean.getCompany());
        if(loggedUser!=null){
            userBean.setLoggedIn(true);
            userBean.setUser(loggedUser);
            return loggedUser;
        }
        throw new ForbiddenException(forbiddenLogin);
    }

    private String makeHash(String plain) throws NoSuchAlgorithmException {
        MessageDigest sha512=MessageDigest.getInstance("SHA-512");
        return Hex.toHexString(sha512.digest(plain.getBytes()));
    }
}
