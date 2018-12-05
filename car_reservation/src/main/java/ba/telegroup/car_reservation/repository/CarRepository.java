package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.common.interfaces.HasCompanyIdAndDeletableRepository;
import ba.telegroup.car_reservation.model.Car;
import ba.telegroup.car_reservation.repository.repositoryCustom.CarRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Integer>, HasCompanyIdAndDeletableRepository<Car> , CarRepositoryCustom {
    Long countAllByCompanyIdAndDeleted(Integer companyId,Byte deleted);
    List<Car> getAllByLocationIdAndDeleted(Integer locationId,Byte deleted);
    Long countAllByLocationIdAndDeleted(Integer locationId,Byte deleted);
}
