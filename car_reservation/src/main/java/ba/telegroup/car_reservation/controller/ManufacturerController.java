package ba.telegroup.car_reservation.controller;

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
public class ManufacturerController extends GenericController<Manufacturer,Integer> {
    @Value("${badRequest.delete}")
    private String badRequestDelete;
    @Value("${badRequest.manufacturer.insert.exists}")
    private String badRequestInsertExists;
    @Value("${badRequest.manufacturer.insert.invalid}")
    private String badRequestInsertInvalid;
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    public ManufacturerController(ManufacturerRepository manufacturerRepository){
        super(manufacturerRepository);
        this.manufacturerRepository=manufacturerRepository;
    }

    @Override
    public String delete(Integer integer) throws BadRequestException {
        throw new BadRequestException(badRequestDelete);
    }

    @Transactional
    @Override
    public Manufacturer insert(@RequestBody Manufacturer object) throws BadRequestException, ForbiddenException {
        if(object!=null && object.getName()!=null) {
            if (manufacturerRepository.countAllByName(object.getName()) == 0) {
                return super.insert(object);
            }
            throw new BadRequestException(badRequestInsertExists);
        }
        throw new BadRequestException(badRequestInsertInvalid);
    }
}
