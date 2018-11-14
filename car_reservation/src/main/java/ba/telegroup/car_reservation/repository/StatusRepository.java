package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status,Integer> {
}
