package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.common.interfaces.DeletableRepository;
import ba.telegroup.car_reservation.model.Car;
import ba.telegroup.car_reservation.repository.repositoryCustom.CarRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Integer>, DeletableRepository , CarRepositoryCustom {
}
