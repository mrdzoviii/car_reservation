package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import ba.telegroup.car_reservation.model.LoginBean;
import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.model.modelCustom.UserLocationCompany;
import ba.telegroup.car_reservation.repository.UserRepository;
import ba.telegroup.car_reservation.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RequestMapping("api/user")
@RestController
@Scope("request")
public class UserController extends GenericHasCompanyIdAndDeletableController<User,Integer> {
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
    private Notification notification;
    @Autowired
    public UserController(UserRepository userRepository, Notification notification){
        super(userRepository);
        this.userRepository=userRepository;
        this.notification = notification;
    }

    @Override
    public List getAll(){
        if(userBean.getUser().getRoleId().equals(systemAdmin)){
            return userRepository.getAllExtendedSystemAdmins();
        }
        return userRepository.getAllExtendedByCompanyId(userBean.getUser().getCompanyId());
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public UserLocationCompany login(@RequestBody LoginBean bean) throws ForbiddenException {
        UserLocationCompany loggedUser= userRepository.login(bean.getUsername(),bean.getPassword(),bean.getCompany());
        if(loggedUser!=null){
            userBean.setLoggedIn(true);
            userBean.setUser(loggedUser);
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
    public UserLocationCompany check() throws ForbiddenException {
        if(userBean.getLoggedIn()){
            return userBean.getUser();
        }
        throw new ForbiddenException(forbiddenAction);
    }

    @RequestMapping(value="/company/{id}",method = RequestMethod.GET)
    public List<UserLocationCompany> getAllUsersByCompany(@PathVariable("id") Integer id){
        if(Integer.valueOf(0).equals(id))
            return userRepository.getAllExtendedSystemAdmins();
        return userRepository.getAllExtendedByCompanyId(id);
    }
    @RequestMapping(value="/mail/{mailTo}")
    public Boolean sendMail(@PathVariable("mailTo") String mailTo){
        return notification.sendMail(mailTo,"TEST MAIL","DJUKA JE MAJMUN");
    }

    @Transactional
    @Override
    public User insert(@RequestBody User user) throws BadRequestException, ForbiddenException {

        return super.insert(user);
    }
}
