package ba.telegroup.car_reservation.repository.repositoryCustom.repositoryImpl;

import ba.telegroup.car_reservation.common.CustomRepositoryImpl;
import ba.telegroup.car_reservation.model.modelCustom.CarManufacturerModelFuelLocationCompany;
import ba.telegroup.car_reservation.repository.repositoryCustom.CarRepositoryCustom;

import java.util.List;

public class CarRepositoryImpl extends CustomRepositoryImpl implements CarRepositoryCustom {
    private final String SQL_GET_ALL_EXTENDED_BY_COMPANY="select c.id,c.model_id,c.location_id,c.plate_number,c.deleted,m.engine,m.model,m.transmission,m.year,m.image,g.fuel as fuel_name,m.fuel_id,m.manufacturer_id,p.name as manufacturer_name,l.name as location_name,l.longitude as longitude,l.latitude as latitude,f.name as company_name,f.id as company_id from car c inner join model m on c.model_id = m.id" +
            "                    inner join fuel g on m.fuel_id = g.id" +
            "                    inner join manufacturer p on m.manufacturer_id = p.id" +
            "                    inner join location l on c.location_id = l.id" +
            "                    inner join company f on l.company_id = f.id where f.deleted=0 and f.id=?";

    private final String SQL_GET_ALL_EXTENDED_BY_LOCATION="select c.id,c.model_id,c.location_id,c.plate_number,c.deleted,m.engine,m.model,m.transmission,m.year,m.image,g.fuel as fuel_name,m.fuel_id,m.manufacturer_id,p.name as manufacturer_name,l.name as location_name,l.longitude as longitude,l.latitude as latitude,f.name as company_name,f.id as company_id from car c inner join model m on c.model_id = m.id" +
            "                    inner join fuel g on m.fuel_id = g.id" +
            "                    inner join manufacturer p on m.manufacturer_id = p.id" +
            "                    inner join location l on c.location_id = l.id" +
            "                    inner join company f on l.company_id = f.id where f.deleted=0 and c.location_id=?";

    private final String SQL_GET_EXTENDED_BY_ID="select c.id,c.model_id,c.location_id,c.plate_number,c.deleted,m.engine,m.model,m.transmission,m.year,m.image,g.fuel as fuel_name,m.fuel_id,m.manufacturer_id,p.name as manufacturer_name,l.name as location_name,l.longitude as longitude,l.latitude as latitude,f.name as company_name,f.id as company_id from car c inner join model m on c.model_id = m.id" +
            "                    inner join fuel g on m.fuel_id = g.id" +
            "                    inner join manufacturer p on m.manufacturer_id = p.id" +
            "                    inner join location l on c.location_id = l.id" +
            "                    inner join company f on l.company_id = f.id where f.deleted=0 and c.id=?";

    private final String SQL_GET_EXTENDED_BY_START_AND_END_TIME="select c.id,c.model_id,c.location_id,c.plate_number,c.deleted,m.engine,m.model,m.transmission,m.year,m.image,g.fuel as fuel_name,m.fuel_id,m.manufacturer_id,p.name as manufacturer_name,l.name as location_name,l.longitude as longitude,l.latitude as latitude,f.name as company_name,f.id as company_id from car c inner join model m on c.model_id = m.id\n" +
            "                               inner join fuel g on m.fuel_id = g.id" +
            "                              inner join manufacturer p on m.manufacturer_id = p.id" +
            "                               inner join location l on c.location_id = l.id" +
            "                             inner join company f on l.company_id = f.id  where f.deleted=0 and f.id=? and c.id" +
            "    not in(select car_id from reservation where ((start_time between CAST(? as datetime) and cast(? as datetime)) or" +
            "          (end_time between CAST(? as datetime) and cast(? as datetime)) or (start_time <= CAST(? as DATETIME) and end_time >= CAST(? as DATETIME))) and deleted=0 and state_id != 3)";

    @Override
    public List getAllExtendedByCompanyId(Integer companyId) {
        return entityManager.createNativeQuery(SQL_GET_ALL_EXTENDED_BY_COMPANY,"CarManufacturerModelFuelLocationCompanyMapping")
                .setParameter(1,companyId).getResultList();
    }

    @Override
    public List getAllExtendedByLocationId(Integer locationId) {
         return entityManager.createNativeQuery(SQL_GET_ALL_EXTENDED_BY_LOCATION,"CarManufacturerModelFuelLocationCompanyMapping")
                .setParameter(1,locationId).getResultList();
    }

    @Override
    public CarManufacturerModelFuelLocationCompany getExtendedById(Integer id) {
        return (CarManufacturerModelFuelLocationCompany) entityManager.createNativeQuery(SQL_GET_EXTENDED_BY_ID,"CarManufacturerModelFuelLocationCompanyMapping")
                .setParameter(1,id).getResultStream().findFirst().orElse(null);
    }

    @Override
    public List getAllExtendedByCompanyIdAndFreeBeetweenStartAndEndTime(Integer companyId, String startTime, String endTime) {
        return entityManager.createNativeQuery(SQL_GET_EXTENDED_BY_START_AND_END_TIME,"CarManufacturerModelFuelLocationCompanyMapping")
                .setParameter(1,companyId).setParameter(2,startTime).setParameter(3,endTime).setParameter(4,startTime)
                .setParameter(5,endTime).setParameter(6,startTime).setParameter(7,endTime).getResultList();
    }
}
