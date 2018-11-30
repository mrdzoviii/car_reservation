package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import ba.telegroup.car_reservation.model.Reservation;
import ba.telegroup.car_reservation.repository.ReservationRepository;
import ba.telegroup.car_reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    @Value("${action.success}")
    private String actionSuccess;
    @Value("${forbidden.action}")
    private String forbiddenAction;
    @Value("${badRequest.reservation.delete}")
    private String badRequestReservationDelete;
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository, UserRepository userRepository) {
        super(reservationRepository);
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List getAll() throws ForbiddenException {
        return reservationRepository.getAllExtendedByCompanyId(userBean.getUser().getCompanyId());
    }


    @Transactional(rollbackFor = Exception.class)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Reservation insert(@RequestBody Reservation reservation) throws BadRequestException, ForbiddenException {
        if (checkReservation(reservation)) {

            return reservationRepository.getExtendedById(super.insert(reservation).getId());
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

    @Override
    public String delete(@PathVariable  Integer id) throws BadRequestException, ForbiddenException {
        Reservation reservation=reservationRepository.findById(id).orElse(null);
        if(reservation!=null && reservation.getStateId().equals(stateReserved) && reservation.getUserId().equals(userBean.getUser().getId())) {
            return super.delete(id);
        }
        throw new BadRequestException(badRequestReservationDelete);
    }

    private Boolean checkReservation(Reservation reservation){
        return reservation != null && reservation.getCarId() != null && reservation.getCompanyId() != null && reservation.getDeleted() != null &&
                reservation.getDeleted().equals(notDeleted) &&
                reservation.getDirection() != null && !reservation.getDirection().isEmpty() && reservation.getDirection().length() <= directionLength &&
                reservation.getStateId() != null && reservation.getStateId().equals(stateReserved) && reservation.getUserId() != null
                && (userRepository.findById(reservation.getUserId()).orElse(null)) != null && userBean.getUser().getId().equals(reservation.getUserId())
                && reservation.getStartTime() != null && reservation.getEndTime() != null &&
                reservation.getStartTime().before(reservation.getEndTime());
    }
}
