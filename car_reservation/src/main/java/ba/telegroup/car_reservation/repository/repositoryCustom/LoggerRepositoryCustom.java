package ba.telegroup.car_reservation.repository.repositoryCustom;

import ba.telegroup.car_reservation.model.modelCustom.LoggerUserCompany;

import java.util.List;

public interface LoggerRepositoryCustom {
    List<LoggerUserCompany> getAllExtended();
}
