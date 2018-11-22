package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.common.interfaces.HasCompanyIdAndDeletableRepository;
import ba.telegroup.car_reservation.model.Location;
import ba.telegroup.car_reservation.repository.repositoryCustom.LocationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Integer>, HasCompanyIdAndDeletableRepository<Location>, LocationRepositoryCustom {
}
