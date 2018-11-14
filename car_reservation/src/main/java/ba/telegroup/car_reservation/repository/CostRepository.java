package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostRepository extends JpaRepository<Cost,Integer> {
}
