package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import ba.telegroup.car_reservation.model.Car;
import ba.telegroup.car_reservation.model.Location;
import ba.telegroup.car_reservation.model.Reservation;
import ba.telegroup.car_reservation.repository.CarRepository;
import ba.telegroup.car_reservation.repository.ExpenseRepository;
import ba.telegroup.car_reservation.repository.LocationRepository;
import ba.telegroup.car_reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
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
    @Value("${badRequest.delete}")
    private String badRequestDelete;
    @Value("${deleted.yes}")
    private Byte deleted;
    @Value("${deleted.not}")
    private Byte notDeleted;
    @Value("${action.success}")
    private String success;
    private Boolean result;
    private LocationRepository locationRepository;
    private CarController carController;
    private CarRepository carRepository;

    @Autowired
    public LocationController(LocationRepository locationRepository, CarController carController,CarRepository carRepository){
        super(locationRepository);
        this.locationRepository=locationRepository;
        this.carController=carController;
        this.carRepository=carRepository;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String delete(@PathVariable Integer id) throws BadRequestException, ForbiddenException {
        List<Car> cars=carRepository.getAllByLocationIdAndDeleted(id,notDeleted);
        result=true;
        cars.forEach(car -> {
            try {
                if(carController.delete(car.getId()).equals(success)){
                    setResult(true);
                }
            } catch (BadRequestException | ForbiddenException e) {
                setResult(false);
            }
        });
        if(result) {
            return super.delete(id);
        }
        throw new BadRequestException(badRequestDelete);
    }

    private void setResult(Boolean bool){
        result&=bool;
    }
}
