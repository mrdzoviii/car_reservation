package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelRepository extends JpaRepository<Fuel,Integer> {
}
