package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericDeletableController;
import ba.telegroup.car_reservation.model.Car;
import ba.telegroup.car_reservation.model.Manufacturer;
import ba.telegroup.car_reservation.model.Model;
import ba.telegroup.car_reservation.model.modelCustom.CarManufacturerModelFuelLocationCompany;
import ba.telegroup.car_reservation.repository.CarRepository;
import ba.telegroup.car_reservation.repository.ManufacturerRepository;
import ba.telegroup.car_reservation.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/car")
@Scope("request")
public class CarController extends GenericDeletableController<Car,Integer> {
    @Value("${car.location.all}")
    private Integer allLocations;
    @Value("${badRequest.car.insert}")
    private String badRequestInsert;
    private CarRepository carRepository;
    private ManufacturerRepository manufacturerRepository;
    private ModelRepository modelRepository;
    private ModelController modelController;
    private ManufacturerController manufacturerController;
    @Autowired
    public CarController(CarRepository carRepository, ManufacturerRepository manufacturerRepository,ModelRepository modelRepository,
                         ManufacturerController manufacturerController,ModelController modelController){
        super(carRepository);
        this.carRepository=carRepository;
        this.manufacturerRepository=manufacturerRepository;
        this.modelRepository=modelRepository;
        this.modelController=modelController;
        this.manufacturerController=manufacturerController;
    }

    @Override
    public List getAll() throws ForbiddenException {
        return carRepository.getAllExtendedByCompanyId(userBean.getUser().getCompanyId());
    }

    @Override
    public Car findById(@PathVariable Integer id) throws ForbiddenException {
        return carRepository.getExtendedById(id);
    }

    @RequestMapping(value = "/company/{id}",method = RequestMethod.GET)
    public List getAllExtendedByCompanyId(@PathVariable("id") Integer id){
        return carRepository.getAllExtendedByCompanyId(id);
    }

    @RequestMapping(value = "/location/{id}",method = RequestMethod.GET)
    public List getAllExtendedByLocationId(@PathVariable("id") Integer id){
        if(id.equals(allLocations)){
            return carRepository.getAllExtendedByCompanyId(userBean.getUser().getCompanyId());
        }
        return carRepository.getAllExtendedByLocationId(id);
    }
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value="/custom",method = RequestMethod.POST)
    public Car insertCarExtended(@RequestBody CarManufacturerModelFuelLocationCompany car) throws BadRequestException, ForbiddenException {
        if(car!=null && car.getBodyStyle()!=null && car.getCompanyId()!=null && car.getCompanyName()!=null && car.getLocationId()!=null &&
        car.getLatitude()!=null && car.getLongitude()!=null && car.getLocationName()!=null && car.getManufacturerName()!=null &&
        car.getModel()!=null && car.getEngine()!=null && car.getType()!=null && car.getTransmission()!=null && car.getDeleted()!=null && car.getPlateNumber()!=null &&
        car.getYear()!=null && car.getImage()!=null && car.getFuelId()!=null){
            Manufacturer manufacturer;
            Car item=new Car();
            if(manufacturerRepository.countAllByName(car.getManufacturerName())==0L){
                manufacturer=new Manufacturer();
                manufacturer.setName(car.getManufacturerName());
                manufacturer=manufacturerController.insert(manufacturer);
                if(manufacturer!=null){
                    car.setManufacturerId(manufacturer.getId());
                }
            }else{
                if(car.getManufacturerId()!=null){
                    Model model;
                    if(modelRepository.countAllByModelAndManufacturerId(car.getModel(),car.getManufacturerId())==0L){
                        model=new Model();
                        model.setBodyStyle(car.getBodyStyle());
                        model.setEngine(car.getEngine());
                        model.setFuelId(car.getFuelId());
                        model.setImage(car.getImage());
                        model.setTransmission(car.getTransmission());
                        model.setType(car.getType());
                        model.setYear(car.getYear());
                        model.setModel(car.getModel());
                        model.setManufacturerId(car.getManufacturerId());
                        model=modelController.insert(model);
                        if(model!=null){
                            car.setModelId(model.getId());
                        }
                    }else{
                        if(car.getModelId()!=null){
                            if(car.getImage()!=null){
                                model=modelRepository.getOne(car.getModelId());
                                if(!Arrays.equals(model.getImage(),car.getImage())) {
                                    model.setImage(car.getImage());
                                    modelController.update(model.getId(),model);
                                }
                            }
                            item.setModelId(car.getModelId());
                            item.setDeleted(car.getDeleted());
                            item.setPlateNumber(car.getPlateNumber());
                            item.setLocationId(car.getLocationId());
                            item.setDeleted(car.getDeleted());
                            item=carRepository.saveAndFlush(item);
                            if(item!=null){
                                logCreateAction(item);
                                return carRepository.getExtendedById(item.getId());
                            }
                        }
                    }
                }
            }
        }
        throw new BadRequestException(badRequestInsert);
    }
}
