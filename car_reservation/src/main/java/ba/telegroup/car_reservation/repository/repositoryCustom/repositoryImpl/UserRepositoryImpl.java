package ba.telegroup.car_reservation.repository.repositoryCustom.repositoryImpl;

import ba.telegroup.car_reservation.common.CustomRepositoryImpl;
import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.model.modelCustom.UserCompany;
import ba.telegroup.car_reservation.repository.repositoryCustom.UserRepositoryCustom;
import org.springframework.transaction.annotation.Transactional;

public class UserRepositoryImpl extends CustomRepositoryImpl implements UserRepositoryCustom {

    private final String SQL_LOGIN="select u.id,u.email,u.username,u.first_name,u.last_name,u.status_id,u.company_id,u.location_id,u.mail_option_id,u.avatar,u.role_id,u.deleted,c.logo as company_logo,c.name as company_name from user u inner join company c on u.company_id = c.id " +
            " where username=? and lower(password)=lower(SHA2(?,512)) and c.name=? and u.status_id=1 and u.deleted=0";
    private final String SQL_LOGIN_NO_COMPANY="select u.id,u.email,u.username,u.first_name,u.last_name,u.status_id,u.company_id,u.location_id,u.mail_option_id,u.avatar,u.role_id,u.deleted,c.logo as company_logo,c.name as company_name from user u left join company c on u.company_id=c.id where u.username=? and lower(u.password)=lower(SHA2(?,512)) and u.company_id is null " +
            "and u.status_id=1 and u.deleted=0";

    private final String SQL_DELETE_BY_COMPANY="update user u set u.deleted=1 where company_id=?";

    @Override
    public UserCompany login(String username, String password, String companyName) {
        if("".equals(companyName)){
            return  (UserCompany) entityManager.createNativeQuery(SQL_LOGIN_NO_COMPANY,"UserCompanyMapping").setParameter(1,username).
                    setParameter(2,password).getResultStream().findFirst().orElse(null);
        }
        return (UserCompany) entityManager.createNativeQuery(SQL_LOGIN,"UserCompanyMapping").setParameter(1,username).
                setParameter(2,password).setParameter(3,companyName).getResultStream().findFirst().orElse(null);
    }

    @Override
    public Boolean deleteUsersByCompanyId(Integer companyId) {
        return entityManager.createNativeQuery(SQL_DELETE_BY_COMPANY).setParameter(1,companyId).executeUpdate()>0;
    }

}
