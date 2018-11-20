package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.common.interfaces.DeletableRepository;
import ba.telegroup.car_reservation.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Integer>,DeletableRepository {
}
