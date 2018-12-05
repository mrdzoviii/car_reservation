package ba.telegroup.car_reservation.repository.repositoryCustom;

import ba.telegroup.car_reservation.model.Expense;
import ba.telegroup.car_reservation.model.modelCustom.ExpenseCarReservationUser;

import java.util.List;

public interface ExpenseRepositoryCustom {
    List getAllExtendedByReservationId(Integer reservationId);
    List getAllExtended();
    List getAllExtendedByCarId(Integer carId);
    ExpenseCarReservationUser getExtendedById(Integer id);
    List<Expense> getAllExpensesByCompanyIdAndDateBetween(Integer companyId, String dateFrom, String dateTo);
    Long deleteByCompanyId(Integer companyId);
    Long deleteByUserId(Integer userId);
    Long deleteByCarId(Integer carId);
}
