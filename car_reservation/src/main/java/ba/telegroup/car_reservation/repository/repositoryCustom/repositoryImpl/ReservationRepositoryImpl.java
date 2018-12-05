package ba.telegroup.car_reservation.repository.repositoryCustom.repositoryImpl;

import ba.telegroup.car_reservation.common.CustomRepositoryImpl;
import ba.telegroup.car_reservation.model.modelCustom.ReservationStateCarCompanyUser;
import ba.telegroup.car_reservation.model.modelCustom.ReservationStateCarUserCompanyLocation;
import ba.telegroup.car_reservation.repository.repositoryCustom.ReservationRepositoryCustom;

import java.util.List;

public class ReservationRepositoryImpl extends CustomRepositoryImpl implements ReservationRepositoryCustom {
    private final String SQL_GET_ALL_EXTENDED_BY_COMPANY="select r.id,r.created_time,r.start_time,r.end_time,r.start_mileage,r.finish_mileage,r.direction,r.state_id,s.state,r.user_id,r.deleted,r.car_id," +
            "u.username,concat(u.first_name,' ',u.last_name) as full_name,concat(m2.name,' ',m.model) as car_name,v.plate_number,m.engine,m.image,m.transmission,f.fuel as fuel_name,m.fuel_id,m.manufacturer_id,m2.name as manufacturer_name,r.company_id,c.name as company_name,m.year,m.model" +
            " from reservation r inner join state s on r.state_id = s.id inner join user u on r.user_id = u.id inner join company c on r.company_id = c.id" +
            " inner join car v on r.car_id = v.id inner join model m on v.model_id = m.id inner join manufacturer m2 on m.manufacturer_id = m2.id inner join fuel f on m.fuel_id = f.id where r.company_id=? and r.deleted=0 order by r.start_time desc,r.end_time desc";

    private final String SQL_GET_EXTENDED_BY_ID="select r.id,r.created_time,r.start_time,r.end_time,r.start_mileage,r.finish_mileage,r.direction,r.state_id,s.state,r.user_id,r.deleted,r.car_id," +
            "u.username,concat(u.first_name,' ',u.last_name) as full_name,concat(m2.name,' ',m.model) as car_name,v.plate_number,m.engine,m.image,m.transmission,f.fuel as fuel_name,m.fuel_id,m.manufacturer_id,m2.name as manufacturer_name,r.company_id,c.name as company_name,m.year,m.model" +
            " from reservation r inner join state s on r.state_id = s.id inner join user u on r.user_id = u.id inner join company c on r.company_id = c.id" +
            " inner join car v on r.car_id = v.id inner join model m on v.model_id = m.id inner join manufacturer m2 on m.manufacturer_id = m2.id inner join fuel f on m.fuel_id = f.id where r.id=? and r.deleted=0 order by r.start_time desc,r.end_time desc";

    private final String SQL_GET_RESERVATION_INFO_FOR_NOTIFICATION="select r.id,r.car_id,r.company_id,r.deleted,r.user_id,r.direction,r.state_id,s.state,concat(c.plate_number,' - ',m.name,' ',k.model) as model,concat(r.start_time,' - ',r.end_time) as period,c.location_id,concat(l.name,' @',l.address) as location_name,u.username,concat(u.first_name,' ',u.last_name) as full_name " +
            "from reservation r inner  join car c on r.car_id = c.id inner join location l on c.location_id = l.id inner join company f on r.company_id = f.id inner join model k on c.model_id = k.id inner join manufacturer m on k.manufacturer_id = m.id inner join user u on r.user_id = u.id inner join state s " +
            "on r.state_id = s.id where r.deleted=0 and r.id=? order by r.start_time desc,r.end_time desc";

    private final String SQL_GET_ALL_EXTENDED_BY_CAR_ID="select r.id,r.created_time,r.start_time,r.end_time,r.start_mileage,r.finish_mileage,r.direction,r.state_id,s.state,r.user_id,r.deleted,r.car_id," +
            "u.username,concat(u.first_name,' ',u.last_name) as full_name,concat(m2.name,' ',m.model) as car_name,v.plate_number,m.engine,m.image,m.transmission,f.fuel as fuel_name,m.fuel_id,m.manufacturer_id,m2.name as manufacturer_name,r.company_id,c.name as company_name,m.year,m.model" +
            " from reservation r inner join state s on r.state_id = s.id inner join user u on r.user_id = u.id inner join company c on r.company_id = c.id" +
            " inner join car v on r.car_id = v.id inner join model m on v.model_id = m.id inner join manufacturer m2 on m.manufacturer_id = m2.id inner join fuel f on m.fuel_id = f.id where r.car_id=? and r.deleted=0 order by r.start_time desc,r.end_time desc";

    private final String SQL_DELETE_BY_COMPANY="update reservation u set u.deleted=1 where company_id=?";

    private final String SQL_DELETE_BY_USER="update reservation u set u.deleted=1 where user_id=?";

    private final String SQL_DELETE_BY_CAR="update reservation u set u.deleted=1 where car_id=?";

    @Override
    public List getAllExtendedByCompanyId(Integer companyId) {
        return entityManager.createNativeQuery(SQL_GET_ALL_EXTENDED_BY_COMPANY,"ReservationStateCarCompanyUserMapping").
                setParameter(1,companyId).getResultList();
    }

    @Override
    public ReservationStateCarCompanyUser getExtendedById(Integer id) {
        return (ReservationStateCarCompanyUser) entityManager.createNativeQuery(SQL_GET_EXTENDED_BY_ID,"ReservationStateCarCompanyUserMapping").setParameter(1,id).getResultStream().findFirst().orElse(null);
    }

    @Override
    public ReservationStateCarUserCompanyLocation getReservationInfoForNotification(Integer id) {
        return (ReservationStateCarUserCompanyLocation) entityManager.createNativeQuery(SQL_GET_RESERVATION_INFO_FOR_NOTIFICATION,"ReservationStateCarUserCompanyLocationMapping")
                .setParameter(1,id).getResultStream().findFirst().orElse(null);
    }

    @Override
    public List getAllExtendedByCarId(Integer carId){
        return entityManager.createNativeQuery(SQL_GET_ALL_EXTENDED_BY_CAR_ID,"ReservationStateCarCompanyUserMapping").
                setParameter(1,carId).getResultList();
    }

    @Override
    public Long deleteByCompanyId(Integer companyId) {
        return Long.valueOf(entityManager.createNativeQuery(SQL_DELETE_BY_COMPANY).setParameter(1,companyId).executeUpdate());
    }

    @Override
    public Long deleteByUserId(Integer userId) {
        return Long.valueOf(entityManager.createNativeQuery(SQL_DELETE_BY_USER).setParameter(1,userId).executeUpdate());
    }

    @Override
    public Long deleteByCarId(Integer carId) {
        return Long.valueOf(entityManager.createNativeQuery(SQL_DELETE_BY_CAR).setParameter(1,carId).executeUpdate());
    }
}
