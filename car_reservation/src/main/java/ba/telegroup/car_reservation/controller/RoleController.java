package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.controller.genericController.GenericController;
import ba.telegroup.car_reservation.model.Role;
import ba.telegroup.car_reservation.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/role")
@Controller
@Scope("request")
public class RoleController extends GenericController<Role,Integer>{
    @Value("${badRequest.insert}")
    private String badRequestInsert;

    @Value("${badRequest.update}")
    private String badRequestUpdate;

    @Value("${badRequest.delete}")
    private String badRequestDelete;
    private RoleRepository roleRepository;
    public RoleController(RoleRepository roleRepository){
        super(roleRepository);
        this.roleRepository=roleRepository;
    }

    @Override
    public String delete(Integer integer) throws BadRequestException {
        throw new BadRequestException(badRequestDelete);
    }

    @Override
    public Role insert(Role object) throws BadRequestException{
        throw new BadRequestException(badRequestInsert);
    }

    @Override
    public String update(Integer integer, Role object) throws BadRequestException {
        throw new BadRequestException(badRequestUpdate);
    }
}
