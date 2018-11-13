package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdController;
import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.repository.UserRepository;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequestMapping("/api/user")
@Controller
@Scope("request")
public class UserController extends GenericHasCompanyIdController<User,Integer> {
    private UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository){
        super(userRepository);
        this.userRepository=userRepository;
    }

    private String makeHash(String plain) throws NoSuchAlgorithmException {
        MessageDigest sha512=MessageDigest.getInstance("SHA-512");
        return Hex.toHexString(sha512.digest(plain.getBytes()));
    }
}
