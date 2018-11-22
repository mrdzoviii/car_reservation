package ba.telegroup.car_reservation.repository;
import ba.telegroup.car_reservation.common.interfaces.HasCompanyIdRepository;
import ba.telegroup.car_reservation.model.User;
import ba.telegroup.car_reservation.repository.repositoryCustom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer>, HasCompanyIdRepository<User> , UserRepositoryCustom {
    Long countAllByCompanyIdAndDeleted(Integer companyId,Byte deleted);
}
