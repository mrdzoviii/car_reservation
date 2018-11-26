package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import ba.telegroup.car_reservation.model.Location;
import ba.telegroup.car_reservation.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/location")
@Scope("request")
public class LocationController extends GenericHasCompanyIdAndDeletableController<Location,Integer> {
    @Value("${forbidden.action}")
    private String forbiddenAction;
    private LocationRepository locationRepository;
    @Autowired
    public LocationController(LocationRepository locationRepository){
        super(locationRepository);
        this.locationRepository=locationRepository;
    }

    @RequestMapping(path = "/company/{id}",method = RequestMethod.GET)
    public List<Location> getAllByCompanyId(@PathVariable("id") Integer id){
        return locationRepository.getAllByCompanyId(id);
    }

    @Override
    public List<Location> getAll() throws ForbiddenException {
        if(userBean.getUser()!=null) {
            if (userBean.getUser().getCompanyId() != null) {
                return locationRepository.getAllByCompanyId(userBean.getUser().getCompanyId());
            }
        }
        throw new ForbiddenException(forbiddenAction);
    }
}
