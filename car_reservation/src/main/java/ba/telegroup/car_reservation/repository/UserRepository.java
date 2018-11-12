package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.common.interfaces.HasCompanyIdAndDeletableRepository;
import ba.telegroup.car_reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>,HasCompanyIdAndDeletableRepository<User> {
}
