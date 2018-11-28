package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model,Integer> {
    List<Model>  getAllByManufacturerId(Integer id);
    Long countAllByModelAndManufacturerId(String model,Integer manufacturerId);
}
