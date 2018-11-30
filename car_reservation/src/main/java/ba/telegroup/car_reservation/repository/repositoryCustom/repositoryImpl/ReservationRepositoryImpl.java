package ba.telegroup.car_reservation.repository.repositoryCustom.repositoryImpl;

import ba.telegroup.car_reservation.common.CustomRepositoryImpl;
import ba.telegroup.car_reservation.model.modelCustom.ReservationStateCarCompanyUser;
import ba.telegroup.car_reservation.repository.repositoryCustom.ReservationRepositoryCustom;

import java.util.List;

public class ReservationRepositoryImpl extends CustomRepositoryImpl implements ReservationRepositoryCustom {
    private final String SQL_GET_ALL_EXTENDED_BY_COMPANY="select r.id,r.created_time,r.start_time,r.end_time,r.start_mileage,r.finish_mileage,r.direction,r.state_id,s.state,r.user_id,r.deleted,r.car_id," +
            "u.username,concat(u.first_name,' ',u.last_name) as full_name,concat(m2.name,' ',m.model) as car_name,v.plate_number,m.engine,m.image,m.transmission,f.fuel as fuel_name,m.fuel_id,m.manufacturer_id,m2.name as manufacturer_name,r.company_id,c.name as company_name,m.year,m.model" +
            " from reservation r inner join state s on r.state_id = s.id inner join user u on r.user_id = u.id inner join company c on r.company_id = c.id" +
            " inner join car v on r.car_id = v.id inner join model m on v.model_id = m.id inner join manufacturer m2 on m.manufacturer_id = m2.id inner join fuel f on m.fuel_id = f.id where r.company_id=? and r.deleted=0";

    private final String SQL_GET_EXTENDED_BY_ID="select r.id,r.created_time,r.start_time,r.end_time,r.start_mileage,r.finish_mileage,r.direction,r.state_id,s.state,r.user_id,r.deleted,r.car_id," +
            "u.username,concat(u.first_name,' ',u.last_name) as full_name,concat(m2.name,' ',m.model) as car_name,v.plate_number,m.engine,m.image,m.transmission,f.fuel as fuel_name,m.fuel_id,m.manufacturer_id,m2.name as manufacturer_name,r.company_id,c.name as company_name,m.year,m.model" +
            " from reservation r inner join state s on r.state_id = s.id inner join user u on r.user_id = u.id inner join company c on r.company_id = c.id" +
            " inner join car v on r.car_id = v.id inner join model m on v.model_id = m.id inner join manufacturer m2 on m.manufacturer_id = m2.id inner join fuel f on m.fuel_id = f.id where r.id=? and r.deleted=0";

    @Override
    public List getAllExtendedByCompanyId(Integer companyId) {
        return entityManager.createNativeQuery(SQL_GET_ALL_EXTENDED_BY_COMPANY,"ReservationStateCarCompanyUserMapping").
                setParameter(1,companyId).getResultList();
    }

    @Override
    public ReservationStateCarCompanyUser getExtendedById(Integer id) {
        return (ReservationStateCarCompanyUser) entityManager.createNativeQuery(SQL_GET_EXTENDED_BY_ID,"ReservationStateCarCompanyUserMapping").setParameter(1,id).getResultStream().findFirst().orElse(null);
    }
}
