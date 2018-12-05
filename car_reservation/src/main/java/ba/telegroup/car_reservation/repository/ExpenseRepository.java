package ba.telegroup.car_reservation.repository;

import ba.telegroup.car_reservation.common.interfaces.DeletableRepository;
import ba.telegroup.car_reservation.model.Expense;
import ba.telegroup.car_reservation.repository.repositoryCustom.ExpenseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExpenseRepository extends JpaRepository<Expense,Integer>, DeletableRepository<Expense>, ExpenseRepositoryCustom {
    Long countExpenseByReservationIdAndDeleted(Integer reservationId,Byte deleted);
    Long countAllByCompanyIdAndDeleted(Integer companyId,Byte deleted);
}
