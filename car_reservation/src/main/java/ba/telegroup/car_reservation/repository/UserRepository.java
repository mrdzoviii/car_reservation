package ba.telegroup.car_reservation.repository;
import ba.telegroup.car_reservation.common.interfaces.HasCompanyIdAndDeletableRepository;
import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.repository.repositoryCustom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User,Integer>, HasCompanyIdAndDeletableRepository<User> , UserRepositoryCustom {
    Long countAllByCompanyIdAndDeleted(Integer companyId,Byte deleted);
    Long countAllUsersByEmailAndDeleted(String email,Byte deleted);
    User getByTokenAndDeleted(String token,Byte deleted);
    User getByUsernameAndDeleted(String username,Byte deleted);
    List<User> getAllByCompanyIdAndRoleIdAndDeleted(Integer companyId,Integer roleId,Byte deleted);
}
