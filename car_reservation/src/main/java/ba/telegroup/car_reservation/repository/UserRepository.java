package ba.telegroup.car_reservation.repository;
import ba.telegroup.car_reservation.common.interfaces.HasCompanyIdRepository;
import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.repository.repositoryCustom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer>, HasCompanyIdRepository<User> , UserRepositoryCustom {
    List<User> getAllByCompanyIdAndDeleted(Integer companyId,Byte deleted);
    List<User> getAllByRoleIdAndDeleted(Integer roleId,Byte deleted);
    Long countAllByCompanyIdAndDeleted(Integer companyId,Byte deleted);
}
