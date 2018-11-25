package ba.telegroup.car_reservation.repository.repositoryCustom.repositoryImpl;

import ba.telegroup.car_reservation.common.CustomRepositoryImpl;
import ba.telegroup.car_reservation.model.modelCustom.LoggerUserCompany;
import ba.telegroup.car_reservation.repository.repositoryCustom.LoggerRepositoryCustom;

import java.util.List;

public class LoggerRepositoryImpl extends CustomRepositoryImpl implements LoggerRepositoryCustom {

    private final String SQL_GET_ALL_EXTENDED="select l.id,l.action_type,l.action_details,l.table_name,l.created,l.user_id,l.atomic,l.company_id,u.username as user_username,ifnull(c.name,'') as company_name,u.role_id as user_role from logger l inner join user u on l.user_id = u.id left join company c on l.company_id = c.id order by l.created desc";

    public List<LoggerUserCompany> getAllExtended() {
        return entityManager.createNativeQuery(SQL_GET_ALL_EXTENDED,"LoggerUserCompanyMapping").getResultList();
    }
}
