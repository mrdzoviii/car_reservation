package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.ReadOnlyController;
import ba.telegroup.car_reservation.model.Role;
import ba.telegroup.car_reservation.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/role")
@RestController
@Scope("request")
public class RoleController extends ReadOnlyController<Role,Integer> {
    private RoleRepository roleRepository;
    @Value("${role.system_admin}")
    private Integer roleSystemAdmin;
    @Autowired
    public RoleController(RoleRepository roleRepository){
        super(roleRepository);
        this.roleRepository=roleRepository;
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public List<Role> getAllUserRoles(){
        return roleRepository.getAllByIdNot(roleSystemAdmin);
    }
}
