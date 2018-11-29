package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import ba.telegroup.car_reservation.model.Reservation;
import ba.telegroup.car_reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/reservation")
@Scope("request")
public class ReservationController extends GenericHasCompanyIdAndDeletableController<Reservation,Integer> {
    private ReservationRepository reservationRepository;
    @Autowired
    public ReservationController(ReservationRepository reservationRepository){
        super(reservationRepository);
        this.reservationRepository=reservationRepository;
    }

    @Override
    public List getAll() throws ForbiddenException {
        return reservationRepository.getAllExtendedByCompanyId(userBean.getUser().getCompanyId());
    }
}
