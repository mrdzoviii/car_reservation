package ba.telegroup.car_reservation.repository.repositoryCustom;

import ba.telegroup.car_reservation.model.modelCustom.ExpenseCarReservationUser;

import java.util.List;

public interface ExpenseRepositoryCustom {
    List<ExpenseCarReservationUser> getAllExtendedByReservationId(Integer reservationId);
    List<ExpenseCarReservationUser> getAllExtended();
    ExpenseCarReservationUser getExtendedById(Integer id);
}
