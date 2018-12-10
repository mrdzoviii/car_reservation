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
import ba.telegroup.car_reservation.util.CarReservationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;
import javax.validation.constraints.Pattern;
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
    private ReservationRepository reservationRepository;
    private ExpenseRepository expenseRepository;
    private CarRepository carRepository;
    private Validator validator;

    @Autowired
    public LocationController(LocationRepository locationRepository,ExpenseRepository expenseRepository,
                              ReservationRepository reservationRepository,CarRepository carRepository,Validator validator){
        super(locationRepository);
        this.locationRepository=locationRepository;
        this.reservationRepository=reservationRepository;
        this.expenseRepository=expenseRepository;
        this.carRepository=carRepository;
        this.validator=validator;
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
                if(reservationRepository.deleteByCarId(car.getId()).equals(reservationRepository.countAllByCarIdAndDeleted(car.getId(),deleted))
                    && expenseRepository.deleteByCarId(car.getId()).equals(expenseRepository.countAllByCarIdAndDeleted(car.getId(),deleted))){
                    setResult(true);
                    return;
                }
                    setResult(false);
        });
        if(result && carRepository.deleteByLocationId(id).equals(carRepository.countAllByLocationIdAndDeleted(id,deleted))) {
            return super.delete(id);
        }
        throw new BadRequestException(badRequestDelete);
    }

    @Transactional(rollbackFor = Exception.class)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Location insert(@RequestBody Location object) throws BadRequestException, ForbiddenException {
        CarReservationUtils.validate(object,validator);
        return super.insert(object);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(@PathVariable Integer id,@RequestBody Location object) throws BadRequestException, ForbiddenException {
        CarReservationUtils.validate(object,validator);
        return super.update(id, object);
    }

    private void setResult(Boolean bool){
        result&=bool;
    }
}
