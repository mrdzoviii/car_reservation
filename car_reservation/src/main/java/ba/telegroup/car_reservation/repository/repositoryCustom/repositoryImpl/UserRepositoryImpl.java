package ba.telegroup.car_reservation.repository.repositoryCustom.repositoryImpl;

import ba.telegroup.car_reservation.common.CustomRepositoryImpl;
import ba.telegroup.car_reservation.model.modelCustom.UserLocationCompany;
import ba.telegroup.car_reservation.repository.repositoryCustom.UserRepositoryCustom;

import java.util.List;


public class UserRepositoryImpl extends CustomRepositoryImpl implements UserRepositoryCustom {


    private final String SQL_LOGIN="select u.id,u.email,u.username,u.first_name,u.last_name,u.status_id,u.company_id,u.location_id,u.mail_option_id,u.avatar,u.role_id,u.deleted,c.logo as company_logo,c.name as company_name,ifnull(l.name,'') as location_name,l.address as location_address,l.latitude as location_latitude," +
            "l.longitude as location_longitude ,concat(u.first_name,' ',u.last_name) as full_name from user u inner join company c on u.company_id = c.id left join location l on u.location_id = l.id" +
            " where username=? and lower(password)=lower(SHA2(?,512)) and c.name=? and u.status_id=1 and u.deleted=0 and c.deleted=0";
    private final String SQL_LOGIN_NO_COMPANY="select u.id,u.email,u.username,u.first_name,u.last_name,u.status_id,u.company_id,u.location_id,u.mail_option_id,u.avatar,u.role_id,u.deleted," +
            "c.logo as company_logo,c.name as company_name,ifnull(l.name,'') as location_name,l.address as location_address,l.latitude as location_latitude,l.longitude as location_longitude ,concat(u.first_name,' ',u.last_name) as full_name  from user u left join location l on l.id=u.location_id left join company c on u.company_id=c.id where u.username=? and lower(u.password)=lower(SHA2(?,512)) and u.company_id is null " +
            "and u.status_id=1 and u.deleted=0";

    private final String SQL_GET_ALL_EXTENDED_BY_COMPANY_ID="select u.id,u.email,u.username,u.first_name,u.last_name,u.status_id,u.company_id,u.location_id,u.mail_option_id,u.avatar,u.role_id,u.deleted,c.logo as company_logo,c.name as company_name,ifnull(l.name,'') as location_name,l.address as location_address,l.latitude as location_latitude," +
            "l.longitude as location_longitude,concat(u.first_name,' ',u.last_name) as full_name  from user u inner join company c on u.company_id = c.id left join location l on u.location_id = l.id" +
            " where u.company_id=? and u.deleted=0 and c.deleted=0 and u.username is not null";

    private final String SQL_GET_ALL_EXTENDED_SYSTEM_ADMINS="select u.id,u.email,u.username,u.first_name,u.last_name,u.status_id,u.company_id,u.location_id,u.mail_option_id,u.avatar,u.role_id,u.deleted,c.logo as company_logo,c.name as company_name,ifnull(l.name,'') as location_name,l.address as location_address,l.latitude as location_latitude," +
            "l.longitude as location_longitude ,concat(u.first_name,' ',u.last_name) as full_name  from user u left join company c on u.company_id = c.id left join location l on u.location_id = l.id" +
            " where u.role_id=1 and u.deleted=0 and u.username is not null";

    private final String SQL_DELETE_BY_COMPANY="update user u set u.deleted=1 where company_id=?";

    private final String SQL_GET_EXTENDED_BY_ID="select u.id,u.email,u.username,u.first_name,u.last_name,u.status_id,u.company_id,u.location_id,u.mail_option_id,u.avatar,u.role_id,u.deleted,c.logo as company_logo,c.name as company_name,ifnull(l.name,'') as location_name,l.address as location_address,l.latitude as location_latitude," +
            "l.longitude as location_longitude ,concat(u.first_name,' ',u.last_name) as full_name  from user u left join company c on u.company_id = c.id left join location l on u.location_id = l.id" +
            " where u.id=? and u.deleted=0";

    @Override
    public UserLocationCompany login(String username, String password, String companyName) {
        if("".equals(companyName)){
            return  (UserLocationCompany) entityManager.createNativeQuery(SQL_LOGIN_NO_COMPANY,"UserLocationCompanyMapping").setParameter(1,username).
                    setParameter(2,password).getResultStream().findFirst().orElse(null);
        }
        return (UserLocationCompany) entityManager.createNativeQuery(SQL_LOGIN,"UserLocationCompanyMapping").setParameter(1,username).
                setParameter(2,password).setParameter(3,companyName).getResultStream().findFirst().orElse(null);
    }

    @Override
    public Long deleteUsersByCompanyId(Integer companyId) {
        return Long.valueOf(entityManager.createNativeQuery(SQL_DELETE_BY_COMPANY).setParameter(1,companyId).executeUpdate());
    }

    @Override
    public List getAllExtendedByCompanyId(Integer companyId) {
        return entityManager.createNativeQuery(SQL_GET_ALL_EXTENDED_BY_COMPANY_ID,"UserLocationCompanyMapping").setParameter(1,companyId).getResultList();
    }

    @Override
    public List getAllExtendedSystemAdmins() {
        return entityManager.createNativeQuery(SQL_GET_ALL_EXTENDED_SYSTEM_ADMINS,"UserLocationCompanyMapping").getResultList();
    }

    @Override
    public UserLocationCompany getExtendedById(Integer id) {
        return (UserLocationCompany) entityManager.createNativeQuery(SQL_GET_EXTENDED_BY_ID,"UserLocationCompanyMapping").setParameter(1,id).getResultStream().findFirst().orElse(null);
    }
}
