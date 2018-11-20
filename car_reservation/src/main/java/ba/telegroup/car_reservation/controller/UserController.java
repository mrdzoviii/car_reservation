package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdController;
import ba.telegroup.car_reservation.model.LoginBean;
import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.model.modelCustom.UserCompany;
import ba.telegroup.car_reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RequestMapping("api/user")
@RestController
@Scope("request")
public class UserController extends GenericHasCompanyIdController<User,Integer> {
    @Value(value = "${forbidden.login}")
    private String forbiddenLogin;
    @Value(value = "${logout.success}")
    private String logoutSuccess;
    @Value(value="${forbidden.action}")
    private String forbiddenAction;
    @Value("${deleted.not}")
    private Byte notDeleted;
    @Value("${role.system_admin}")
    private Integer systemAdmin;
    private UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository){
        super(userRepository);
        this.userRepository=userRepository;
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public UserCompany login(@RequestBody LoginBean bean) throws ForbiddenException {
        UserCompany loggedUser= userRepository.login(bean.getUsername(),bean.getPassword(),bean.getCompany());
        if(loggedUser!=null){
            userBean.setLoggedIn(true);
            userBean.setUser(loggedUser);
            System.out.println("LOGIN"+userBean.getUser().getUsername()+"  "+userBean.getLoggedIn());
            return loggedUser;
        }
        throw new ForbiddenException(forbiddenLogin);
    }

    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request) throws ForbiddenException {
            HttpSession session=request.getSession(false);
        System.out.println("LOGOUT CALLED:"+userBean.getUser().getUsername()+"  "+userBean.getLoggedIn());
            if(session!=null && userBean.getLoggedIn()){
                session.invalidate();
                return logoutSuccess;
            }
        throw new ForbiddenException(forbiddenAction);
    }

    @RequestMapping(value="/state",method = RequestMethod.GET)
    public UserCompany check() throws ForbiddenException {
        if(userBean.getLoggedIn()){
            return userBean.getUser();
        }
        throw new ForbiddenException(forbiddenAction);
    }

    @RequestMapping(value="/company/{id}",method = RequestMethod.GET)
    public List<User> getAllUsersByCompany(@PathVariable("id") Integer id){
        if(Integer.valueOf(0).equals(id))
            return userRepository.getAllByRoleIdAndDeleted(systemAdmin,notDeleted);
        return userRepository.getAllByCompanyIdAndDeleted(id,notDeleted);
    }
}
