package ba.telegroup.car_reservation.repository.repositoryCustom;

import ba.telegroup.car_reservation.model.modelCustom.CarManufacturerModelFuelLocationCompany;

import java.util.List;

public interface CarRepositoryCustom {
     List getAllExtendedByCompanyId(Integer companyId);
     List getAllExtendedByLocationId(Integer locationId);
     CarManufacturerModelFuelLocationCompany getExtendedById(Integer id);
}
