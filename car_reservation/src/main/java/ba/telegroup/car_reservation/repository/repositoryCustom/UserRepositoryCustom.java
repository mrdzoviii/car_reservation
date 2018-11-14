package ba.telegroup.car_reservation.repository.repositoryCustom;

import ba.telegroup.car_reservation.model.User;

public interface UserRepositoryCustom {
    User login(String username,String password,String companyName);
}
