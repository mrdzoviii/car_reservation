package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.ReadOnlyController;
import ba.telegroup.car_reservation.model.Role;
import ba.telegroup.car_reservation.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/role")
@RestController
@Scope("request")
public class RoleController extends ReadOnlyController<Role,Integer> {
    @Autowired
    public RoleController(RoleRepository roleRepository){
        super(roleRepository);
    }
}
