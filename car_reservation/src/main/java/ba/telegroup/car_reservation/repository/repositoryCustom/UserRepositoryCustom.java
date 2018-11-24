package ba.telegroup.car_reservation.repository.repositoryCustom;



import ba.telegroup.car_reservation.model.modelCustom.UserLocationCompany;

import java.util.List;

public interface UserRepositoryCustom {
    UserLocationCompany login(String username, String password, String companyName);
    Long deleteUsersByCompanyId(Integer companyId);
    List<UserLocationCompany> getAllExtendedByCompanyId(Integer companyId);
    List<UserLocationCompany> getAllExtendedSystemAdmins();
    UserLocationCompany getExtendedById(Integer id);
}
