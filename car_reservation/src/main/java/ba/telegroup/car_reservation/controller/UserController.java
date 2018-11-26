package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import ba.telegroup.car_reservation.model.LoginBean;
import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.model.modelCustom.UserLocationCompany;
import ba.telegroup.car_reservation.repository.UserRepository;
import ba.telegroup.car_reservation.util.Notification;
import ba.telegroup.car_reservation.util.PasswordInfo;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@RequestMapping("api/user")
@RestController
@Scope("request")
public class UserController extends GenericHasCompanyIdAndDeletableController<User,Integer> {
    @Value(value = "${forbidden.login}")
    private String forbiddenLogin;
    @Value("${status.active}")
    private Integer statusActive;
    @Value(value = "${logout.success}")
    private String logoutSuccess;
    @Value(value="${forbidden.action}")
    private String forbiddenAction;
    @Value("${badRequest.user.insert}")
    private String badRequestUserInsert;
    @Value("${badRequest.user.update}")
    private String badRequestUserUpdate;
    @Value("${deleted.not}")
    private Byte notDeleted;
    @Value("${role.system_admin}")
    private Integer systemAdmin;
    @Value("${status.inactive}")
    private Integer statusInactive;
    @Value("${token.length}")
    private Integer tokenLength;
    @Value("${badRequest.user.token}")
    private String badRequestToken;
    @Value("${badRequest.user.register}")
    private String badRequestRegister;
    @Value("${badRequest.user.register.username}")
    private String badRequestUsername;
    @Value("${action.success}")
    private String success;
    @Value("${deleted.yes}")
    private Byte deleted;
    @Value("${badRequest.password}")
    private String badRequestPassword;
    private UserRepository userRepository;
    private Notification notification;
    @Autowired
    public UserController(UserRepository userRepository, Notification notification){
        super(userRepository);
        this.userRepository=userRepository;
        this.notification = notification;
    }

    @Override
    public User findById(@PathVariable Integer id) {
        User object=userRepository.getExtendedById(id);
        if (object == null || object.getDeleted().equals(deleted) || (!userBean.getUser().getRoleId().equals(systemAdmin) && !userBean.getUser().getCompanyId().equals(object.getCompanyId())))
            object = null;
        return object;
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
            if(session!=null && userBean.getLoggedIn()){
                session.invalidate();
                return logoutSuccess;
            }
        throw new ForbiddenException(forbiddenAction);
    }

    @RequestMapping(value="/state",method = RequestMethod.GET)
    public User check() throws ForbiddenException {
        if(userBean.getLoggedIn()){
            return userBean.getUser();
        }
        throw new ForbiddenException(forbiddenAction);
    }

    @RequestMapping(value="/company/{id}",method = RequestMethod.GET)
    public List getAllUsersByCompany(@PathVariable("id") Integer id){
        if(Integer.valueOf(0).equals(id))
            return userRepository.getAllExtendedSystemAdmins();
        return userRepository.getAllExtendedByCompanyId(id);
    }


    @Transactional
    @Override
    public User insert(@RequestBody User user) throws BadRequestException, ForbiddenException {
        if(user!=null && user.getEmail()!=null && user.getStatusId()!=null && user.getRoleId()!=null && user.getDeleted()!=null) {
            if(userRepository.countAllUsersByEmailAndDeleted(user.getEmail(),notDeleted).equals(0L)){
                user.setStatusId(statusInactive);
                user.setToken(RandomStringUtils.randomAlphanumeric(tokenLength));
                notification.sendLink(user.getEmail(),user.getToken());
                User inserted=super.insert(user);
                return userRepository.getExtendedById(inserted.getId());
            }
        }
        throw new BadRequestException(badRequestUserInsert);
    }

    @Transactional
    @Override
    public String update(@PathVariable Integer id,@RequestBody User user) throws BadRequestException, ForbiddenException {
        User dbUser=userRepository.findById(id).orElse(null);
        if(dbUser!=null && user!=null && user.getRoleId() !=null && user.getStatusId() !=null && user.getEmail()!=null){
            //if company or location changed
            if (user.getCompanyId() != null && user.getLocationId() != null) {
                dbUser.setCompanyId(user.getCompanyId());
                dbUser.setLocationId(user.getLocationId());
            }
            if(user.getLastName()!=null){
                    dbUser.setLastName(user.getLastName());
            }
            if(user.getFirstName()!=null){
                dbUser.setFirstName(user.getFirstName());
            }
            if(user.getAvatar()!=null){
                dbUser.setAvatar(user.getAvatar());
            }
            if(user.getMailOptionId()!=null){
                dbUser.setMailOptionId(user.getMailOptionId());
            }
            dbUser.setRoleId(user.getRoleId());
            dbUser.setStatusId(user.getStatusId());
            if (!dbUser.getEmail().equals(user.getEmail()) && userRepository.countAllUsersByEmailAndDeleted(user.getEmail(), notDeleted) > 0)
                throw new BadRequestException(badRequestUserUpdate);
            dbUser.setEmail(user.getEmail());
            if(super.update(id,dbUser).equals(success)){
                System.out.println(userBean.getUser().getId()+"  "+dbUser.getId());
                if(userBean.getUser().getId().equals(dbUser.getId())){
                    userBean.setUser(userRepository.getExtendedById(dbUser.getId()));
                }
                return success;
            }
            return super.update(id, dbUser);
        }
        throw new BadRequestException(badRequestUserUpdate);
    }

    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    public Integer getUserByToken(@PathVariable("token") String token) throws BadRequestException {
        User user=userRepository.getByTokenAndDeleted(token,notDeleted);
        if(user!=null){
            return user.getId();
        }
        throw new BadRequestException(badRequestToken);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String registerUser(@RequestBody User data) throws BadRequestException, ForbiddenException {
        User user=userRepository.findById(data.getId()).orElse(null);
        if(user!=null && data.getUsername()!=null && data.getFirstName()!=null && data.getLastName()!=null && data.getPassword()!=null){
                if(userRepository.getByUsernameAndDeleted(data.getUsername(),notDeleted)!=null){
                    throw new BadRequestException(badRequestUsername);
                }
                user.setStatusId(statusActive);
                user.setToken(null);
                user.setUsername(data.getUsername());
                user.setFirstName(data.getFirstName());
                user.setLastName(data.getLastName());
                user.setPassword(hashPassword(data.getPassword()));
                UserLocationCompany temp=userBean.getUser();
                userBean.setUser(userRepository.getExtendedById(user.getId()));
                super.update(user.getId(),user);
                userBean.setUser(temp);
                return success;
        }
        throw new BadRequestException(badRequestRegister);
    }

    @RequestMapping(value = "/password",method = RequestMethod.POST)
    public String changePassword(@RequestBody PasswordInfo passwordInfo) throws BadRequestException, ForbiddenException {
        User user=userRepository.findById(userBean.getUser().getId()).orElse(null);
        if(user!=null && passwordInfo !=null) {
            if(passwordInfo.getOldPassword()!=null &&user.getPassword().toLowerCase().equals(hashPassword(passwordInfo.getOldPassword().trim().toLowerCase()))){
                if(passwordInfo.getNewPassword()!=null && passwordInfo.getNewPasswordRepeated()!=null
                        && passwordInfo.getNewPassword().trim().equals(passwordInfo.getNewPasswordRepeated().trim())){
                    user.setPassword(hashPassword(passwordInfo.getNewPassword()));
                    return super.update(user.getId(),user);
                }
            }
        }
        throw new BadRequestException(badRequestPassword);
    }

    private String hashPassword( String plainText)  {
        MessageDigest digest= null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
            return Hex.encodeHexString(digest.digest(plainText.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

}
