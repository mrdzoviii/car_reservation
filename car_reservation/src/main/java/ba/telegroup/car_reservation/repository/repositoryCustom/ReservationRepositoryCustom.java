package ba.telegroup.car_reservation.repository.repositoryCustom;

import ba.telegroup.car_reservation.model.modelCustom.ReservationStateCarCompanyUser;
import ba.telegroup.car_reservation.model.modelCustom.ReservationStateCarUserCompanyLocation;

import java.util.List;

public interface ReservationRepositoryCustom {
    List getAllExtendedByCompanyId(Integer companyId);
    ReservationStateCarCompanyUser getExtendedById(Integer id);
    ReservationStateCarUserCompanyLocation getReservationInfoForNotification(Integer id);
    List getAllExtendedByCarId(Integer carId);
    Long deleteByCompanyId(Integer companyId);
}
