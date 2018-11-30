package ba.telegroup.car_reservation.repository.repositoryCustom;

import ba.telegroup.car_reservation.model.modelCustom.ReservationStateCarCompanyUser;

import java.util.List;

public interface ReservationRepositoryCustom {
    List getAllExtendedByCompanyId(Integer companyId);
    ReservationStateCarCompanyUser getExtendedById(Integer id);
}
