package ba.telegroup.car_reservation.repository.repositoryCustom;

import java.util.List;

public interface ReservationRepositoryCustom {
    List getAllExtendedByCompanyId(Integer companyId);
}
