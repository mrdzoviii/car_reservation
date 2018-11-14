package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.ReadOnlyController;
import ba.telegroup.car_reservation.model.Status;
import ba.telegroup.car_reservation.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("request")
@RequestMapping("/api/status")
public class StatusController extends ReadOnlyController<Status,Integer> {
    @Autowired
    public StatusController(StatusRepository statusRepository) {
        super(statusRepository);
    }
}
