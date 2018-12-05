package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericDeletableController;
import ba.telegroup.car_reservation.model.Car;
import ba.telegroup.car_reservation.model.Manufacturer;
import ba.telegroup.car_reservation.model.Model;
import ba.telegroup.car_reservation.model.modelCustom.CarManufacturerModelFuelLocationCompany;
import ba.telegroup.car_reservation.repository.*;
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
    @Value("${badRequest.car.update}")
    private String badRequestUpdate;
    @Value("${action.success}")
    private String actionSuccess;
    @Value("${badRequest.delete}")
    private String badRequestDelete;
    @Value("${deleted.yes}")
    private Byte deleted;
    private CarRepository carRepository;
    private ManufacturerRepository manufacturerRepository;
    private ModelRepository modelRepository;
    private ModelController modelController;
    private ManufacturerController manufacturerController;
    private ReservationRepository reservationRepository;
    private ExpenseRepository expenseRepository;
    @Autowired
    public CarController(CarRepository carRepository, ManufacturerRepository manufacturerRepository,ModelRepository modelRepository,
                         ManufacturerController manufacturerController,ModelController modelController,
                         ExpenseRepository expenseRepository,ReservationRepository reservationRepository){
        super(carRepository);
        this.carRepository=carRepository;
        this.manufacturerRepository=manufacturerRepository;
        this.modelRepository=modelRepository;
        this.modelController=modelController;
        this.manufacturerController=manufacturerController;
        this.expenseRepository=expenseRepository;
        this.reservationRepository=reservationRepository;
    }

    @Override
    public List getAll() throws ForbiddenException {
        return carRepository.getAllExtendedByCompanyId(userBean.getUser().getCompanyId());
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
    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public CarManufacturerModelFuelLocationCompany insertCarExtended(@RequestBody CarManufacturerModelFuelLocationCompany car) throws BadRequestException, ForbiddenException {
        if (checkCustom(car)) {
            car.setManufacturerId(getManufacturerId(car.getManufacturerName()));
            car.setModelId(getModelId(car.getModel(),car.getEngine(),car.getTransmission(),car.getYear(),car.getManufacturerId(),car.getImage(),car.getFuelId()));
            Car item = new Car();
            item.setModelId(car.getModelId());
            item.setDeleted(car.getDeleted());
            item.setPlateNumber(car.getPlateNumber());
            item.setLocationId(car.getLocationId());
            item.setDeleted(car.getDeleted());
            item.setCompanyId(car.getCompanyId());
            item = insert(item);
            if (item != null) {
                        return carRepository.getExtendedById(item.getId());
                    }
                }
        throw new BadRequestException(badRequestInsert);
    }
    @RequestMapping(value = "/reservation/{startTime}/{endTime}",method = RequestMethod.GET)
    public List getFreeCars(@PathVariable("startTime") String startTime,@PathVariable("endTime") String endTime){
        return carRepository.getAllExtendedByCompanyIdAndFreeBetweenStartAndEndTime(userBean.getUser().getCompanyId(),startTime,endTime);
    }
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/custom/{id}",method = RequestMethod.PUT)
    public CarManufacturerModelFuelLocationCompany updateExtended(@PathVariable("id") Integer id,@RequestBody CarManufacturerModelFuelLocationCompany car) throws BadRequestException, ForbiddenException {
        Car dbCar=carRepository.findById(id).orElse(null);
        if (dbCar!=null && checkCustom(car)) {
            car.setManufacturerId(getManufacturerId(car.getManufacturerName()));
            car.setModelId(getModelId(car.getModel(),car.getEngine(),car.getTransmission(),car.getYear(),car.getManufacturerId(),car.getImage(),car.getFuelId()));
                    if (dbCar.getModelId() != car.getModelId()) {
                        dbCar.setModelId(car.getModelId());
                    }
                    if(dbCar.getPlateNumber()!=car.getPlateNumber()){
                        dbCar.setPlateNumber(car.getPlateNumber());
                    }
                    if(dbCar.getDeleted()!= car.getDeleted()){
                        dbCar.setDeleted(car.getDeleted());
                    }
                    if(dbCar.getLocationId()!=car.getLocationId()){
                        dbCar.setLocationId(car.getLocationId());
                    }
                    if(update(id,dbCar).equals(actionSuccess)){
                        return carRepository.getExtendedById(id);
                    }
            }
        throw new BadRequestException(badRequestUpdate);
    }
    private Integer getManufacturerId(String manufacturerName) throws BadRequestException, ForbiddenException {
        if (manufacturerRepository.countAllByName(manufacturerName) == 0L) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName(manufacturerName);
            manufacturer = manufacturerController.insert(manufacturer);
            if (manufacturer == null) {
                throw new BadRequestException(badRequestInsert);
            }
            return manufacturer.getId();
        }
        return manufacturerRepository.getByName(manufacturerName).getId();
    }
    private Integer getModelId(String model,String engine,String transmission,String year,Integer manufacturerId,byte[] image,Integer fuelId) throws BadRequestException, ForbiddenException {
        Model item = new Model();
        item.setEngine(engine);
        item.setFuelId(fuelId);
        item.setImage(image);
        item.setTransmission(transmission);
        item.setYear(year);
        item.setModel(model);
        item.setManufacturerId(manufacturerId);
        if (modelRepository.countAllByModelAndManufacturerId(model, manufacturerId) == 0L) {
            item = modelController.insert(item);
            if (item == null) {
                throw new BadRequestException(badRequestInsert);
            }
            return item.getId();
        }else{
            final Model target=item;
            List<Model> models=modelRepository.getAllByModel(model);
            if(models.stream().filter(m->m.equalsData(target)).count()==0){
                item=modelController.insert(item);
                if(item==null){
                    throw new BadRequestException(badRequestInsert);
                }
                return item.getId();
            }else{
                Model ret= models.stream().filter(m->m.equalsData(target)).findFirst().orElse(null);
                if(ret==null){
                    throw new BadRequestException(badRequestInsert);
                }
                if(!Arrays.equals(ret.getImage(),target.getImage())){
                    ret.setImage(image);
                    modelController.update(ret.getId(),ret);
                }
                return ret.getId();
            }
        }
    }
    private Boolean checkCustom(CarManufacturerModelFuelLocationCompany car){
        return car != null  && car.getCompanyId() != null && car.getCompanyName() != null && car.getLocationId() != null &&
                car.getLatitude() != null && car.getLongitude() != null && car.getLocationName() != null && car.getManufacturerName() != null &&
                car.getModel() != null && car.getEngine() != null && car.getTransmission() != null && car.getDeleted() != null && car.getPlateNumber() != null &&
                car.getYear() != null && car.getImage() != null && car.getFuelId() != null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String delete(@PathVariable Integer id) throws BadRequestException, ForbiddenException {
        if(expenseRepository.deleteByCarId(id).equals(expenseRepository.countAllByCarIdAndDeleted(id,deleted)) &&
            reservationRepository.deleteByCarId(id).equals(reservationRepository.countAllByCarIdAndDeleted(id,deleted))) {
            return super.delete(id);
        }
        throw new BadRequestException(badRequestDelete);
    }
}
