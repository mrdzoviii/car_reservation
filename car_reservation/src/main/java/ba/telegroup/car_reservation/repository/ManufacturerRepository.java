package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.common.interfaces.DeletableRepository;
import ba.telegroup.car_reservation.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ManufacturerRepository extends JpaRepository<Manufacturer,Integer>,DeletableRepository {

}
