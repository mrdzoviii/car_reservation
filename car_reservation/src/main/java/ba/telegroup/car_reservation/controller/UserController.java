package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import ba.telegroup.car_reservation.model.*;
import ba.telegroup.car_reservation.model.modelCustom.UserLocationCompany;
import ba.telegroup.car_reservation.repository.*;
import ba.telegroup.car_reservation.util.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Validator;
import java.util.List;





@RequestMapping("api/user")
@RestController
@Scope("request")
public class UserController extends GenericHasCompanyIdAndDeletableController<User,Integer> {
    @Value("${reset.password.length}")
    private Integer randomPasswordLength;
    @Value(value = "${badRequest.user.login}")
    private String badRequestLogin;
    @Value("${badRequest.delete}")
    private String badRequestDelete;
    @Value("${status.active}")
    private Integer statusActive;
    @Value(value = "${logout.success}")
    private String logoutSuccess;
    @Value(value="${forbidden.action}")
    private String forbiddenAction;
    @Value("${badRequest.user.nouser}")
    private String badRequestNoUser;
    @Value("${badRequest.user.email}")
    private String badRequestEmail;
    @Value("${deleted.not}")
    private Byte notDeleted;
    @Value("${role.system_admin}")
    private Integer systemAdmin;
    @Value("${role.company_admin}")
    private Integer companyAdmin;
    @Value("${role.user}")
    private Integer companyUser;
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
    @Value("${badRequest.user.password}")
    private String badRequestPassword;
    @Value("${badRequest.user.insert.user.missing}")
    private String badRequestMissData;
    @Value("${badRequest.user.insert.company}")
    private String badRequestCompany;
    @Value("${badRequest.user.insert.location}")
    private String badRequestLocation;
    @Value("${badRequest.user.insert.role}")
    private String badRequestRole;
    @Value("${badRequest.user.insert.status}")
    private String badRequestStatus;
    @Value("${badRequest.user.insert.mailoption}")
    private String badRequestMailOption;
    @Value("${badRequest.user.reset.fail}")
    private String badRequestPasswordFail;
    @Value("${badRequest.user.reset.incorrect}")
    private String badRequestIncorrectData;
    private UserRepository userRepository;
    private Notification notification;
    private MailOptionRepository mailOptionRepository;
    private ExpenseRepository expenseRepository;
    private ReservationRepository reservationRepository;
    private CompanyRepository companyRepository;
    private LocationRepository locationRepository;
    private RoleRepository roleRepository;
    private StatusRepository statusRepository;
    private Validator validator;
    @Autowired
    public UserController(UserRepository userRepository, Notification notification,MailOptionRepository mailOptionRepository,
                          ExpenseRepository expenseRepository,LocationRepository locationRepository,RoleRepository roleRepository,
                          StatusRepository statusRepository,
                          ReservationRepository reservationRepository,CompanyRepository companyRepository,Validator validator){
        super(userRepository);
        this.userRepository=userRepository;
        this.notification = notification;
        this.mailOptionRepository=mailOptionRepository;
        this.expenseRepository=expenseRepository;
        this.reservationRepository=reservationRepository;
        this.companyRepository=companyRepository;
        this.locationRepository=locationRepository;
        this.validator=validator;
        this.roleRepository=roleRepository;
        this.statusRepository=statusRepository;
    }


    //checked+
    @Override
    public List getAll(){
        if(userBean.getUser().getRoleId().equals(systemAdmin)){
            return userRepository.getAllExtendedSystemAdmins();
        }
        return userRepository.getAllExtendedByCompanyId(userBean.getUser().getCompanyId());
    }


    //checked+
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public UserLocationCompany login(@RequestBody LoginBean bean) throws BadRequestException {
        UserLocationCompany loggedUser= userRepository.login(bean.getUsername(),bean.getPassword(),bean.getCompany());
        if(loggedUser!=null){
            userBean.setLoggedIn(true);
            userBean.setUser(loggedUser);
            return loggedUser;
        }
        throw new BadRequestException(badRequestLogin);
    }

    //checked+
    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
            HttpSession session=request.getSession(false);
                session.invalidate();
                return logoutSuccess;
    }

    //checked+
    @RequestMapping(value="/state",method = RequestMethod.GET)
    public User check() throws ForbiddenException {
        if(userBean.getLoggedIn()){
            return userBean.getUser();
        }
        throw new ForbiddenException(forbiddenAction);
    }

    //checked+
    @RequestMapping(value="/company/{id}",method = RequestMethod.GET)
    public List getAllUsersByCompany(@PathVariable("id") Integer id){
        if(Integer.valueOf(0).equals(id))
            return userRepository.getAllExtendedSystemAdmins();
        return userRepository.getAllExtendedByCompanyId(id);
    }


    //checked++
    @Transactional
    @Override
    public User insert(@RequestBody  User user) throws BadRequestException, ForbiddenException {
        if(user!=null && user.getRoleId()!=null && user.getStatusId()!=null && user.getDeleted()!=null) {
            if(user.getRoleId().equals(companyUser) && (user.getLocationId()==null || user.getMailOptionId()==null)){
                throw new BadRequestException(badRequestMissData);
            }
            validateUser(user);
            if(userRepository.countAllUsersByEmailAndDeleted(user.getEmail(),notDeleted).equals(0L)){
                user.setStatusId(statusInactive);
                user.setToken(RandomStringUtils.randomAlphanumeric(tokenLength));
                notification.sendLink(user.getEmail(),user.getToken());
                User inserted=super.insert(user);
                return userRepository.getExtendedById(inserted.getId());
            }
            throw new BadRequestException(badRequestEmail);
        }
        throw new BadRequestException(badRequestNoUser);
    }

    //checked+
    @Transactional
    @Override
    public String update(@PathVariable Integer id,@RequestBody User user) throws BadRequestException, ForbiddenException {
        User dbUser=userRepository.findById(id).orElse(null);
        if(dbUser!=null && user!=null && user.getRoleId() !=null && user.getStatusId() !=null && user.getEmail()!=null){
            validateUser(user);
            if (user.getCompanyId() != null || user.getLocationId() != null) {
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
                throw new BadRequestException(badRequestEmail);
            dbUser.setEmail(user.getEmail());
            if(super.update(id,dbUser).equals(success)){
                if(userBean.getUser().getId().equals(dbUser.getId())){
                    userBean.setUser(userRepository.getExtendedById(dbUser.getId()));
                }
                return success;
            }
        }
        throw new BadRequestException(badRequestNoUser);
    }

    //checked+
    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    public Integer getUserByToken(@PathVariable("token") String token) throws BadRequestException {
        User user=userRepository.getByTokenAndDeleted(token,notDeleted);
        if(user!=null){
            return user.getId();
        }
        throw new BadRequestException(badRequestToken);
    }

    //checked++
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@RequestBody RegisterInfo data) throws BadRequestException{
        User user = userRepository.findById(data.getId()).orElse(null);
        if (user != null && data != null) {
            CarReservationUtils.validate(data,validator);
            if (userRepository.getByUsernameAndDeleted(data.getUsername(), notDeleted) != null) {
                throw new BadRequestException(badRequestUsername);
            }
            user.setStatusId(statusActive);
            user.setToken(null);
            user.setUsername(data.getUsername());
            user.setFirstName(data.getFirstName());
            user.setLastName(data.getLastName());
            user.setPassword(CarReservationUtils.hashPassword(data.getPassword()));
            validateUser(user);
            if (userRepository.saveAndFlush(user) != null) {
                return success;
            }
        }
        throw new BadRequestException(badRequestRegister);
    }

    //checked++
    @RequestMapping(value = "/password",method = RequestMethod.POST)
    public String changePassword(@RequestBody PasswordInfo passwordInfo) throws BadRequestException, ForbiddenException {
        User user=userRepository.findById(userBean.getUser().getId()).orElse(null);
        if(user!=null && passwordInfo !=null) {
            CarReservationUtils.validate(passwordInfo,validator);
            if(passwordInfo.getOldPassword()!=null &&user.getPassword().toLowerCase().equals(CarReservationUtils.hashPassword(passwordInfo.getOldPassword().trim().toLowerCase()))){
                if(passwordInfo.getNewPassword()!=null && passwordInfo.getNewPasswordRepeated()!=null
                        && passwordInfo.getNewPassword().trim().equals(passwordInfo.getNewPasswordRepeated().trim())){
                    user.setPassword(CarReservationUtils.hashPassword(passwordInfo.getNewPassword()));
                    return super.update(user.getId(),user);
                }
            }
        }
        throw new BadRequestException(badRequestPassword);
    }

    @RequestMapping(value = "/mail-option/{option}", method = RequestMethod.PUT)
    public String changeMailOption(@PathVariable("option") Integer option) throws BadRequestException, ForbiddenException {
        if (option != null && option >= 0) {
            User user = userRepository.findById(userBean.getUser().getId()).orElse(null);
            if (user != null) {
                MailOption mailOption=mailOptionRepository.findById(option).orElse(null);
                if(mailOption!=null){
                    user.setMailOptionId(mailOption.getId());
                    return super.update(user.getId(),user);
                }
            }
        }
        throw new BadRequestException(badRequestMailOption);
    }

    //checked+
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public @ResponseBody
    String resetPassword(@RequestBody LoginInfo userInformation) throws BadRequestException {
        User userTemp = userRepository.getByUsernameAndDeleted(userInformation.getUsername(),notDeleted);
        if (userTemp != null) {
            String companyName=null;
            if(userTemp.getRoleId().equals(companyUser)) {
                Company company = companyRepository.findById(userTemp.getCompanyId()).orElse(null);
                if (company != null) {
                    companyName = company.getName();
                }
            }
            if (((userTemp.getRoleId().equals(companyUser) || userTemp.getRoleId().equals(companyAdmin)) && companyName != null && userInformation.getCompany() != null && companyName.equals(userInformation.getCompany().trim()))
                 || userTemp.getRoleId().equals(systemAdmin)) {
                User user = userRepository.findById(userTemp.getId()).orElse(null);
                String newPassword = RandomStringUtils.randomAlphanumeric(randomPasswordLength);
                user.setPassword(CarReservationUtils.hashPassword(newPassword));
                if(userRepository.saveAndFlush(user) != null){
                    notification.sendNewPassword(user.getEmail(),newPassword);
                    return success;
                }
                throw new BadRequestException(badRequestPasswordFail);
            }
            throw new BadRequestException(badRequestIncorrectData);
        }
        throw new BadRequestException(badRequestNoUser);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String delete(@PathVariable Integer id) throws BadRequestException, ForbiddenException {
        User user=userRepository.findById(id).orElse(null);
        if(user!=null && !user.getId().equals(userBean.getUser().getId())) {
            if(user.getRoleId().equals(companyAdmin) || user.getRoleId().equals(systemAdmin) ||
                    (user.getRoleId().equals(companyUser) && expenseRepository.deleteByUserId(id).equals(expenseRepository.countAllByUserIdAndDeleted(id,deleted)) &&
                            reservationRepository.deleteByUserId(id).equals(reservationRepository.countAllByUserIdAndDeleted(id,deleted)))) {
                return super.delete(id);
            }
        }
        throw new BadRequestException(badRequestDelete);
    }

    private void validateUser(User user) throws BadRequestException {
       CarReservationUtils.validate(user,validator);
        if(user.getCompanyId()!=null){
            Company company=companyRepository.findById(user.getCompanyId()).orElse(null);
            if(company==null){
                throw new BadRequestException(badRequestCompany);
            }
        }
        if(user.getLocationId()!=null){
            Location location=locationRepository.findById(user.getLocationId()).orElse(null);
            if(location==null){
                throw new BadRequestException(badRequestLocation);
            }
        }
        if(user.getRoleId()!=null){
            Role role=roleRepository.findById(user.getRoleId()).orElse(null);
            if(role==null){
                throw new BadRequestException(badRequestRole);
            }
        }
        if(user.getStatusId()!=null){
            Status status=statusRepository.findById(user.getStatusId()).orElse(null);
            if(status==null){
                throw new BadRequestException(badRequestStatus);
            }

        }
        if(user.getMailOptionId()!=null){
            MailOption mailOption=mailOptionRepository.findById(user.getMailOptionId()).orElse(null);
            if(mailOption==null){
                throw new BadRequestException(badRequestMailOption);
            }
        }
    }




}
