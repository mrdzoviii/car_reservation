package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.model.MailOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailOptionRepository extends JpaRepository<MailOption,Integer> {
}
