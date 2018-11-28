package ba.telegroup.car_reservation.repository;
import ba.telegroup.car_reservation.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ManufacturerRepository extends JpaRepository<Manufacturer,Integer>{
    Long countAllByName(String name);
    Manufacturer getByName(String name);
}
