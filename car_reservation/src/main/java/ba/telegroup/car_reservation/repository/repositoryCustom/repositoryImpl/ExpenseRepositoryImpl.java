package ba.telegroup.car_reservation.repository.repositoryCustom.repositoryImpl;

import ba.telegroup.car_reservation.common.CustomRepositoryImpl;
import ba.telegroup.car_reservation.model.Expense;
import ba.telegroup.car_reservation.model.modelCustom.ExpenseCarReservationUser;
import ba.telegroup.car_reservation.repository.repositoryCustom.ExpenseRepositoryCustom;

import java.util.List;

public class ExpenseRepositoryImpl extends CustomRepositoryImpl implements ExpenseRepositoryCustom {
    private String SQL_GET_ALL_EXTENDED_BY_RESERVATION_ID="select e.id,e.cost_id,c2.cost,e.user_id,u.username,concat(u.first_name,' ',u.last_name) as full_name,e.reservation_id,e.price,e.date,e.description,e.car_id,concat(c.plate_number,' - ',m2.name,' ',m.model) as car_model,e.deleted from expense e inner join reservation r on e.reservation_id = r.id inner join user u on r.user_id = u.id inner join car c on r.car_id = c.id inner join model m on c.model_id = m.id inner join manufacturer m2 on m.manufacturer_id = m2.id inner join cost c2 on e.cost_id = c2.id where r.id=? and e.deleted=0 order by date desc,price desc";

    private String SQL_GET_ALL_EXTENDED="select e.id,e.cost_id,c2.cost,e.user_id,u.username,concat(u.first_name,' ',u.last_name) as full_name,e.reservation_id,e.price,e.date,e.description,e.car_id,concat(c.plate_number,' - ',m2.name,' ',m.model) as car_model,e.deleted from expense e inner join reservation r on e.reservation_id = r.id inner join user u on r.user_id = u.id inner join car c on r.car_id = c.id inner join model m on c.model_id = m.id inner join manufacturer m2 on m.manufacturer_id = m2.id inner join cost c2 on e.cost_id = c2.id where e.deleted=0 order by date desc,price desc";

    private String SQL_GET_EXTENDED_BY_ID="select e.id,e.cost_id,c2.cost,e.user_id,u.username,concat(u.first_name,' ',u.last_name) as full_name,e.reservation_id,e.price,e.date,e.description,e.car_id,concat(c.plate_number,' - ',m2.name,' ',m.model) as car_model,e.deleted from expense e inner join reservation r on e.reservation_id = r.id inner join user u on r.user_id = u.id inner join car c on r.car_id = c.id inner join model m on c.model_id = m.id inner join manufacturer m2 on m.manufacturer_id = m2.id inner join cost c2 on e.cost_id = c2.id where e.deleted=0 and e.id=? order by date desc,price desc";

    private String SQL_GET_EXTENDED_BY_CAR_ID="select e.id,e.cost_id,c2.cost,e.user_id,u.username,concat(u.first_name,' ',u.last_name) as full_name,e.reservation_id,e.price,e.date,e.description,e.car_id,concat(c.plate_number,' - ',m2.name,' ',m.model) as car_model,e.deleted from expense e inner join reservation r on e.reservation_id = r.id inner join user u on r.user_id = u.id inner join car c on r.car_id = c.id inner join model m on c.model_id = m.id inner join manufacturer m2 on m.manufacturer_id = m2.id inner join cost c2 on e.cost_id = c2.id where e.deleted=0 and e.car_id=? order by date desc,price desc";

    private String SQL_GET_ALL_BY_COMPANY_ID_AND_DATE_BETWEEN="select e.id,e.deleted,e.description,e.date,e.reservation_id,e.user_id,e.cost_id,e.car_id,e.price from expense e inner join user u on e.user_id = u.id inner join company c on u.company_id = c.id\n" +
            "where e.deleted=0 and c.id=? and e.date between cast(? as DATE ) and cast(? as DATE)";

    @Override
    public List getAllExtendedByReservationId(Integer reservationId) {
        return entityManager.createNativeQuery(SQL_GET_ALL_EXTENDED_BY_RESERVATION_ID,"ExpenseCarReservationUserMapping")
                .setParameter(1,reservationId).getResultList();
    }

    @Override
    public List getAllExtended() {
        return entityManager.createNativeQuery(SQL_GET_ALL_EXTENDED,"ExpenseCarReservationUserMapping")
                .getResultList();
    }

    @Override
    public ExpenseCarReservationUser getExtendedById(Integer id) {
        return (ExpenseCarReservationUser) entityManager.createNativeQuery(SQL_GET_EXTENDED_BY_ID,"ExpenseCarReservationUserMapping")
                .setParameter(1,id).getResultStream().findFirst().orElse(null);
    }

    @Override
    public List getAllExtendedByCarId(Integer carId) {
         return entityManager.createNativeQuery(SQL_GET_EXTENDED_BY_CAR_ID,"ExpenseCarReservationUserMapping")
                .setParameter(1,carId).getResultList();
    }

    @Override
    public List<Expense> getAllExpensesByCompanyIdAndDateBetween(Integer companyId, String dateFrom, String dateTo) {
        return entityManager.createNativeQuery(SQL_GET_ALL_BY_COMPANY_ID_AND_DATE_BETWEEN,"ExpenseMapping")
                .setParameter(1,companyId).setParameter(2,dateFrom).setParameter(3,dateTo).getResultList();
    }
}
