package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.ReadOnlyController;
import ba.telegroup.car_reservation.model.Cost;
import ba.telegroup.car_reservation.repository.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("request")
@RequestMapping("api/cost")
public class CostController extends ReadOnlyController<Cost,Integer> {
    @Autowired
    public CostController(CostRepository costRepository){
        super(costRepository);
    }
}
