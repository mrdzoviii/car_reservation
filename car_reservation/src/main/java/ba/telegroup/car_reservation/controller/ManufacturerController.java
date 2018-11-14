package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.controller.genericController.GenericDeletableController;
import ba.telegroup.car_reservation.model.Manufacturer;
import ba.telegroup.car_reservation.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/manufacturer")
@RestController
@Scope("request")
public class ManufacturerController extends GenericDeletableController<Manufacturer,Integer> {
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    public ManufacturerController(ManufacturerRepository manufacturerRepository){
        super(manufacturerRepository);
        this.manufacturerRepository=manufacturerRepository;
    }
}
