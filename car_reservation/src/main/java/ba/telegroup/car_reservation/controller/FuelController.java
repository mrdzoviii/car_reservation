package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.ReadOnlyController;
import ba.telegroup.car_reservation.model.Fuel;
import ba.telegroup.car_reservation.repository.FuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/fuel")
@Scope("request")
public class FuelController extends ReadOnlyController<Fuel,Integer> {
    @Autowired
    public FuelController(FuelRepository fuelRepository){
        super(fuelRepository);
    }
}
