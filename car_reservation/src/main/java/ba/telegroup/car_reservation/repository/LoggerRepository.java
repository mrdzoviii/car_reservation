package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.model.Logger;
import ba.telegroup.car_reservation.repository.repositoryCustom.LoggerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggerRepository extends JpaRepository<Logger,Integer>, LoggerRepositoryCustom {
}
