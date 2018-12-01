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
import ba.telegroup.car_reservation.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;


@RestController
@RequestMapping("api/reservation")
@Scope("request")
public class ReservationController extends GenericHasCompanyIdAndDeletableController<Reservation, Integer> {
    @Value("${badRequest.reservation.insert}")
    private String badRequestReservationInsert;
    @Value("${badRequest.reservation.update}")
    private String badRequestReservationUpdate;
    @Value("${deleted.not}")
    private Byte notDeleted;
    @Value("${reservation.direction.length}")
    private Integer directionLength;
    @Value("${state.reserved}")
    private Integer stateReserved;
    @Value("${state.running}")
    private Integer stateRunning;
    @Value("${state.finished}")
    private Integer stateFinished;
    @Value("${state.completed}")
    private Integer stateCompleted;
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
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private Notification notification;
    private ExpenseRepository expenseRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository, UserRepository userRepository,Notification notification,
                                 ExpenseRepository expenseRepository) {
        super(reservationRepository);
        this.notification=notification;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.expenseRepository=expenseRepository;
    }

    @Override
    public List getAll() throws ForbiddenException {
        return reservationRepository.getAllExtendedByCompanyId(userBean.getUser().getCompanyId());
    }

    @RequestMapping(value = "/car/{id}",method = RequestMethod.GET)
    public List getAllByCarId(@PathVariable("id") Integer id) throws ForbiddenException {
        return reservationRepository.getAllExtendedByCarId(id);
    }


    @Transactional(rollbackFor = Exception.class)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Reservation insert(@RequestBody Reservation reservation) throws BadRequestException, ForbiddenException {
        if (checkReservation(reservation)) {
            reservation=super.insert(reservation);
            ReservationStateCarUserCompanyLocation reservationInfo=reservationRepository.getReservationInfoForNotification(reservation.getId());
            List<User> users=userRepository.getAllByCompanyIdAndRoleIdAndDeleted(reservationInfo.getCompanyId(),roleUser,notDeleted);
            notification.sendNotification(reservationInfo,false,users);
            return reservationRepository.getExtendedById(reservation.getId());
        }
        throw new BadRequestException(badRequestReservationInsert);
    }

    @Override
    public String update(Integer integer, Reservation object) throws BadRequestException, ForbiddenException {
        throw new ForbiddenException(forbiddenAction);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/custom/{id}", method = RequestMethod.PUT)
    public Reservation updateCustom(@PathVariable("id") Integer id, @RequestBody Reservation reservation) throws BadRequestException, ForbiddenException {
        if (checkReservation(reservation)) {
            if (super.update(id, reservation).equals(actionSuccess)) {
                return reservationRepository.getExtendedById(reservation.getId());
            }
        }
        throw new BadRequestException(badRequestReservationUpdate);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String delete(@PathVariable  Integer id) throws BadRequestException, ForbiddenException {
        Reservation reservation=reservationRepository.findById(id).orElse(null);
        Timestamp now=new Timestamp(System.currentTimeMillis());
        if(reservation!=null && reservation.getStateId().equals(stateReserved) && reservation.getUserId().equals(userBean.getUser().getId()) && reservation.getStartTime().after(now)) {
            ReservationStateCarUserCompanyLocation reservationInfo=reservationRepository.getReservationInfoForNotification(id);
            if(super.delete(id).equals(actionSuccess)){
                List<User> users=userRepository.getAllByCompanyIdAndRoleIdAndDeleted(reservationInfo.getCompanyId(),roleUser,notDeleted);
                notification.sendNotification(reservationInfo,true,users);
                return actionSuccess;
            }
        }
        throw new BadRequestException(badRequestReservationDelete);
    }

    @RequestMapping(value = "/start/{id}/{startMileage}",method = RequestMethod.PUT)
    public ReservationStateCarCompanyUser runReservation(@PathVariable("id") Integer id,@PathVariable("startMileage") Integer startMileage) throws BadRequestException, ForbiddenException {
        Reservation reservation=reservationRepository.findById(id).orElse(null);

        Timestamp now=new Timestamp(System.currentTimeMillis());
        if(reservation!=null && reservation.getStateId().equals(stateReserved) && reservation.getStartMileage()==null && startMileage!=null && reservation.getStartTime().before(now)
              && reservation.getUserId().equals(userBean.getUser().getId())){
            reservation.setStartMileage(startMileage);
            reservation.setStateId(stateRunning);
            if(super.update(id,reservation).equals(actionSuccess)){
                return reservationRepository.getExtendedById(reservation.getId());
            }
        }
        throw new BadRequestException(badRequestReservationStart);
    }

    @RequestMapping(value = "/finish/{id}/{finishMileage}",method = RequestMethod.PUT)
    public ReservationStateCarCompanyUser finishReservation(@PathVariable("id") Integer id,@PathVariable("finishMileage") Integer finishMileage) throws BadRequestException, ForbiddenException {
        Reservation reservation=reservationRepository.findById(id).orElse(null);
        Timestamp now=new Timestamp(System.currentTimeMillis());
        if(reservation!=null && reservation.getStateId().equals(stateRunning) && reservation.getUserId().equals(userBean.getUser().getId()) &&
                reservation.getFinishMileage()==null && finishMileage!=null && reservation.getStartTime().before(now)){
            reservation.setFinishMileage(finishMileage);
            reservation.setStateId(stateFinished);
            if(super.update(id,reservation).equals(actionSuccess)){
                return reservationRepository.getExtendedById(reservation.getId());
            }
        }
        throw new BadRequestException(badRequestReservationFinish);
    }

    @RequestMapping(value = "/complete/{id}",method = RequestMethod.PUT)
    public ReservationStateCarCompanyUser completeReservation(@PathVariable("id") Integer id) throws BadRequestException, ForbiddenException {
        Reservation reservation=reservationRepository.findById(id).orElse(null);
        if(reservation!=null && reservation.getStateId().equals(stateFinished) && reservation.getUserId().equals(userBean.getUser().getId())
        && expenseRepository.countExpenseByReservationIdAndDeleted(reservation.getId(),notDeleted)>0){
            reservation.setStateId(stateCompleted);
            if(super.update(id,reservation).equals(actionSuccess)){
                return reservationRepository.getExtendedById(reservation.getId());
            }
        }
        throw new BadRequestException(badRequestReservationComplete);
    }

    private Boolean checkReservation(Reservation reservation){
        Timestamp now=new Timestamp(System.currentTimeMillis());
        return reservation != null && reservation.getCarId() != null && reservation.getCompanyId() != null && reservation.getDeleted() != null &&
                reservation.getDeleted().equals(notDeleted) &&
                reservation.getDirection() != null && !reservation.getDirection().isEmpty() && reservation.getDirection().length() <= directionLength &&
                reservation.getStateId() != null && reservation.getStateId().equals(stateReserved) && reservation.getUserId() != null
                && (userRepository.findById(reservation.getUserId()).orElse(null)) != null && userBean.getUser().getId().equals(reservation.getUserId())
                && reservation.getStartTime() != null && reservation.getEndTime() != null && reservation.getStartTime().after(now) &&
                reservation.getStartTime().before(reservation.getEndTime());
    }

    @RequestMapping(value = "/info/{id}",method = RequestMethod.GET)
    public ReservationStateCarUserCompanyLocation getInfo(@PathVariable("id") Integer id){
        return reservationRepository.getReservationInfoForNotification(id);
    }

}
