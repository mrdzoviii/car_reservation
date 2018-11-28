package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.ReadOnlyController;
import ba.telegroup.car_reservation.model.State;
import ba.telegroup.car_reservation.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/state")
@Scope("request")
public class StateController extends ReadOnlyController<State,Integer> {
    private StateRepository stateRepository;
    @Autowired
    public StateController(StateRepository stateRepository){
        super(stateRepository);
        this.stateRepository=stateRepository;
    }
}
