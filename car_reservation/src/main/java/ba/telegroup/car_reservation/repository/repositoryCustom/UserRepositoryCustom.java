package ba.telegroup.car_reservation.repository.repositoryCustom;

import ba.telegroup.car_reservation.model.modelCustom.UserCompany;

import java.util.List;

public interface UserRepositoryCustom {
    UserCompany login(String username, String password, String companyName);

}
