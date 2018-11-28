package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.common.interfaces.HasCompanyIdAndDeletableRepository;
import ba.telegroup.car_reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Integer>, HasCompanyIdAndDeletableRepository<Reservation> {

}
