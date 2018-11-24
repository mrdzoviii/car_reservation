package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer>{
    List<Role> getAllByIdNot(Integer idNot);
}
