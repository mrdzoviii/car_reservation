package ba.telegroup.car_reservation.repository.repositoryCustom.repositoryImpl;

import ba.telegroup.car_reservation.common.CustomRepositoryImpl;
import ba.telegroup.car_reservation.model.Location;
import ba.telegroup.car_reservation.repository.repositoryCustom.LocationRepositoryCustom;

import java.util.List;

public class LocationRepositoryImpl extends CustomRepositoryImpl implements LocationRepositoryCustom {
    private final String SQL_GET_ALL_LOCATION_BY_COMPANY="select l.id,l.name,l.address,l.latitude,l.longitude,l.deleted,l.company_id from location l inner join company c on l.company_id = c.id where c.deleted=0 and l.company_id=? and l.deleted=0";
    private final String SQL_DELETE_BY_COMPANY="update location u set u.deleted=1 where company_id=?";

    @Override
    public List<Location> getAllByCompanyId(Integer companyId) {
        return entityManager.createNativeQuery(SQL_GET_ALL_LOCATION_BY_COMPANY,"LocationMapping").setParameter(1,companyId).getResultList();
    }

    @Override
    public Long deleteByCompanyId(Integer companyId) {
        return Long.valueOf(entityManager.createNativeQuery(SQL_DELETE_BY_COMPANY).setParameter(1,companyId).executeUpdate());
    }
}
