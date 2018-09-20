package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.controller.genericController.GenericController;
import ba.telegroup.car_reservation.model.Role;
import ba.telegroup.car_reservation.repository.RoleRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/role")
@Controller
@Scope("request")
public class RoleController extends GenericController<Role,Integer> {
    private RoleRepository roleRepository;
    public RoleController(RoleRepository roleRepository){
        super(roleRepository);
        this.roleRepository=roleRepository;
    }

}
