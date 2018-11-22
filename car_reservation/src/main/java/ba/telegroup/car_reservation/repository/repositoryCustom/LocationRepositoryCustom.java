package ba.telegroup.car_reservation.repository.repositoryCustom;

import ba.telegroup.car_reservation.model.Location;

import java.util.List;

public interface LocationRepositoryCustom {
    List<Location> getAllByCompanyId(Integer companyId);
}
