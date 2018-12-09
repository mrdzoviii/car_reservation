package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import ba.telegroup.car_reservation.model.Reservation;
import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.model.modelCustom.ReservationStateCarCompanyUser;
import ba.telegroup.car_reservation.model.modelCustom.ReservationStateCarUserCompanyLocation;
import ba.telegroup.car_reservation.repository.ExpenseRepository;
import ba.telegroup.car_reservation.repository.ReservationRepository;
import ba.telegroup.car_reservation.repository.UserRepository;
import ba.telegroup.car_reservation.util.CarReservationUtils;
import ba.telegroup.car_reservation.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Validator;
import java.sql.Timestamp;
import java.util.List;



@RestController
@RequestMapping("api/reservation")
@Scope("request")
public class ReservationController extends GenericHasCompanyIdAndDeletableController<Reservation, Integer> {

    @Value("${badRequest.insert}")
    private String badRequestInsert;

    @Value("${badRequest.update}")
    private String badRequestUpdate;

    @Value("${badRequest.reservation.noreservation}")
    private String badRequestNoReservation;

    @Value("${badRequest.reservation.deleted}")
    private String badRequestDeleted;

    @Value("${badRequest.reservation.user}")
    private String badRequestUser;

    @Value("${badRequest.reservation.date}")
    private String badRequestDate;

    @Value("${badRequest.reservation.state.reserved}")
    private String badRequestReserved;

    @Value("${badRequest.reservation.startTime.future}")
    private String startTimeFuture;

    @Value("${badRequest.reservation.startTime.past}")
    private String startTimePast;

    @Value("${badRequest.reservation.insert}")
    private String badRequestReservationInsert;
    @Value("${badRequest.reservation.update}")
    private String badRequestReservationUpdate;
    @Value("${deleted.not}")
    private Byte notDeleted;
    @Value("${state.reserved}")
    private Integer stateReserved;
    @Value("${state.running}")
    private Integer stateRunning;
    @Value("${state.finished}")
    private Integer stateFinished;
    @Value("${state.completed}")
    private Integer stateCompleted;
    @Value("${state.canceled}")
    private Integer stateCanceled;
    @Value("${action.success}")
    private String actionSuccess;
    @Value("${forbidden.action}")
    private String forbiddenAction;
    @Value("${badRequest.reservation.delete}")
    private String badRequestReservationDelete;
    @Value("${role.user}")
    private Integer roleUser;
    @Value("${badRequest.reservation.start}")
    private String badRequestReservationStart;
    @Value("${badRequest.reservation.finish}")
    private String badRequestReservationFinish;
    @Value("${badRequest.reservation.complete}")
    private String badRequestReservationComplete;
    @Value("${badRequest.reservation.cancel}")
    private String getBadRequestReservationCancel;
    @Value("${deleted.yes}")
    private Byte deleted;
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private Notification notification;
    private ExpenseRepository expenseRepository;
    private Validator validator;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository, UserRepository userRepository,Notification notification,
                                 ExpenseRepository expenseRepository,Validator validator) {
        super(reservationRepository);
        this.notification=notification;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.expenseRepository=expenseRepository;
        this.validator=validator;
    }

    //checked+
    @Override
    public List getAll() {
        return reservationRepository.getAllExtendedByCompanyId(userBean.getUser().getCompanyId());
    }

    //checked+
    @RequestMapping(value = "/car/{id}",method = RequestMethod.GET)
    public List getAllByCarId(@PathVariable("id") Integer id) {
        return reservationRepository.getAllExtendedByCarId(id);
    }

    @Override
    public Reservation insert(@RequestBody Reservation object) throws BadRequestException {
        throw new BadRequestException(badRequestInsert);
    }

    @Override
    public String update(@PathVariable Integer integer,@RequestBody Reservation object) throws BadRequestException {
        throw new BadRequestException(badRequestUpdate);
    }


    @Transactional(rollbackFor = Exception.class)
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public ReservationStateCarCompanyUser insertCustom(@RequestBody Reservation reservation) throws BadRequestException, ForbiddenException {
        if (checkReservation(reservation)) {
            if(!reservation.getStateId().equals(stateReserved)){
                throw new BadRequestException(badRequestReserved);
            }
            Timestamp now=new Timestamp(System.currentTimeMillis());
            if(!reservation.getStartTime().after(now)){
                throw new BadRequestException(startTimeFuture);
            }
            reservation=reservationRepository.saveAndFlush(reservation);
            if(reservation!=null) {
                logCreateAction(reservation);
                ReservationStateCarUserCompanyLocation reservationInfo = reservationRepository.getReservationInfoForNotification(reservation.getId());
                List<User> users = userRepository.getAllByCompanyIdAndRoleIdAndDeleted(reservationInfo.getCompanyId(), roleUser, notDeleted);
                notification.sendNotification(reservationInfo, false, users);
                return reservationRepository.getExtendedById(reservation.getId());
            }
        }
        throw new BadRequestException(badRequestReservationInsert);
    }



    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/custom/{id}", method = RequestMethod.PUT)
    public ReservationStateCarCompanyUser updateCustom(@PathVariable("id") Integer id, @RequestBody Reservation reservation) throws BadRequestException, ForbiddenException {
        Reservation oldObject=reservationRepository.findById(id).orElse(null);
        if(oldObject!=null) {
            if (checkReservation(reservation)) {
                Timestamp now=new Timestamp(System.currentTimeMillis());
                if(reservation.getStartTime().after(now) && reservation.getStateId().equals(stateReserved)) {
                    if ((reservation = reservationRepository.saveAndFlush(reservation)) != null) {
                        logUpdateAction(reservation, cloner.deepClone(oldObject));
                        return reservationRepository.getExtendedById(reservation.getId());
                    }
                }
            }
            throw new BadRequestException(badRequestReservationUpdate);
        }
        throw new BadRequestException(badRequestNoReservation);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String delete(@PathVariable  Integer id) throws BadRequestException, ForbiddenException {
        if(expenseRepository.deleteByReservationId(id).equals(expenseRepository.countExpenseByReservationIdAndDeleted(id,deleted))){
            return super.delete(id);
        }
        throw new BadRequestException(badRequestReservationDelete);
    }

    @RequestMapping(value = "/cancel/{id}",method = RequestMethod.PUT)
    public ReservationStateCarCompanyUser cancelReservation(@PathVariable("id") Integer id) throws BadRequestException{
        Reservation reservation=reservationRepository.findById(id).orElse(null);
        Timestamp now=new Timestamp(System.currentTimeMillis());
        if(reservation!=null) {
            if(checkReservation(reservation) && reservation.getStateId().equals(stateReserved) && reservation.getStartTime().after(now)) {
                Reservation oldObject = cloner.deepClone(reservation);
                ReservationStateCarUserCompanyLocation reservationInfo = reservationRepository.getReservationInfoForNotification(id);
                reservation.setStateId(stateCanceled);
                CarReservationUtils.validate(reservation, validator);
                if (reservationRepository.saveAndFlush(reservation) != null) {
                    logUpdateAction(reservation, oldObject);
                    List<User> users = userRepository.getAllByCompanyIdAndRoleIdAndDeleted(reservationInfo.getCompanyId(), roleUser, notDeleted);
                    notification.sendNotification(reservationInfo, true, users);
                    return reservationRepository.getExtendedById(reservation.getId());
                }
            }
            throw new BadRequestException(getBadRequestReservationCancel);
        }
        throw new BadRequestException(badRequestNoReservation);
    }


    @RequestMapping(value = "/start/{id}/{startMileage}",method = RequestMethod.PUT)
    public ReservationStateCarCompanyUser runReservation(@PathVariable("id") Integer id,@PathVariable("startMileage") Integer startMileage) throws BadRequestException{
        Reservation reservation=reservationRepository.findById(id).orElse(null);
        Timestamp now=new Timestamp(System.currentTimeMillis());
        if(reservation!=null && reservation.getStateId().equals(stateReserved) && reservation.getStartMileage()==null && startMileage!=null && reservation.getStartTime().before(now)
                && reservation.getEndTime().after(now)
              && reservation.getUserId().equals(userBean.getUser().getId())){
            Reservation oldObject=cloner.deepClone(reservation);
            reservation.setStartMileage(startMileage);
            reservation.setStateId(stateRunning);
            CarReservationUtils.validate(reservation, validator);
            if (updateReservation(reservation, oldObject))
                return reservationRepository.getExtendedById(reservation.getId());
        }
        throw new BadRequestException(badRequestReservationStart);
    }

    @RequestMapping(value = "/finish/{id}/{finishMileage}",method = RequestMethod.PUT)
    public ReservationStateCarCompanyUser finishReservation(@PathVariable("id") Integer id,@PathVariable("finishMileage") Integer finishMileage) throws BadRequestException, ForbiddenException {
        Reservation reservation=reservationRepository.findById(id).orElse(null);
        Timestamp now=new Timestamp(System.currentTimeMillis());
        if(reservation!=null && reservation.getStateId().equals(stateRunning) && reservation.getUserId().equals(userBean.getUser().getId()) &&
                reservation.getFinishMileage()==null && finishMileage!=null && reservation.getStartTime().before(now) &&
                reservation.getEndTime().after(now)){
            Reservation oldObject=cloner.deepClone(reservation);
            reservation.setFinishMileage(finishMileage);
            reservation.setStateId(stateFinished);
            reservation.setEndTime(now);
            CarReservationUtils.validate(reservation, validator);
            if (updateReservation(reservation, oldObject))
                return reservationRepository.getExtendedById(reservation.getId());
        }
        throw new BadRequestException(badRequestReservationFinish);
    }

    @RequestMapping(value = "/complete/{id}",method = RequestMethod.PUT)
    public ReservationStateCarCompanyUser completeReservation(@PathVariable("id") Integer id) throws BadRequestException {
        Reservation reservation=reservationRepository.findById(id).orElse(null);
        if(reservation!=null && reservation.getStateId().equals(stateFinished) && reservation.getUserId().equals(userBean.getUser().getId())
        && expenseRepository.countExpenseByReservationIdAndDeleted(reservation.getId(),notDeleted)>0){
            Reservation oldObject=cloner.deepClone(reservation);
            reservation.setStateId(stateCompleted);
            CarReservationUtils.validate(reservation, validator);
            if (updateReservation(reservation, oldObject))
                return reservationRepository.getExtendedById(reservation.getId());

        }
        throw new BadRequestException(badRequestReservationComplete);
    }

    private boolean updateReservation(Reservation reservation, Reservation oldObject) throws BadRequestException {
        CarReservationUtils.validate(reservation,validator);
        if(reservationRepository.saveAndFlush(reservation)!=null){
            logUpdateAction(reservation,oldObject);
            return true;
        }
        return false;
    }

    private Boolean checkReservation(Reservation reservation) throws BadRequestException {
        if(reservation!=null) {
            CarReservationUtils.validate(reservation,validator);
            if(!reservation.getDeleted().equals(notDeleted)){
                throw new BadRequestException(badRequestDeleted);
            }
            if(!((userRepository.findById(reservation.getUserId()).orElse(null)) != null && userBean.getUser().getId().equals(reservation.getUserId()))){
                throw new BadRequestException(badRequestUser);
            }
            if(! reservation.getStartTime().before(reservation.getEndTime())){
                throw new BadRequestException(badRequestDate);
            }
            return true;
        }
        throw new BadRequestException(badRequestNoReservation);
    }


}
