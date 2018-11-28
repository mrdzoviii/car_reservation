package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericController;
import ba.telegroup.car_reservation.model.Model;
import ba.telegroup.car_reservation.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/model")
@Scope("request")
public class ModelController extends GenericController<Model,Integer> {
    @Value("${forbidden.action}")
    private String forbiddenAction;
    private ModelRepository modelRepository;
    @Autowired
    public ModelController(ModelRepository modelRepository){
        super(modelRepository);
        this.modelRepository=modelRepository;
    }

    @Override
    public String delete(Integer integer) throws ForbiddenException {
        throw new ForbiddenException(forbiddenAction);
    }

    @RequestMapping(value = "/manufacturer/{id}",method = RequestMethod.GET)
    public List<Model> getAllModelsByManufacturerId(@PathVariable("id") Integer id){
        return modelRepository.getAllByManufacturerId(id);
    }


}
