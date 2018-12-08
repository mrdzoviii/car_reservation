package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.ReadOnlyController;
import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericController;
import ba.telegroup.car_reservation.model.Manufacturer;
import ba.telegroup.car_reservation.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/manufacturer")
@RestController
@Scope("request")
public class ManufacturerController extends ReadOnlyController<Manufacturer,Integer> {
    @Autowired
    public ManufacturerController(ManufacturerRepository manufacturerRepository){
        super(manufacturerRepository);
    }
}
