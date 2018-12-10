package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericHasCompanyIdAndDeletableController;
import ba.telegroup.car_reservation.model.Car;
import ba.telegroup.car_reservation.model.Logger;
import ba.telegroup.car_reservation.model.Manufacturer;
import ba.telegroup.car_reservation.model.Model;
import ba.telegroup.car_reservation.model.modelCustom.CarManufacturerModelFuelLocationCompany;
import ba.telegroup.car_reservation.repository.*;
import ba.telegroup.car_reservation.util.CarReservationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/car")
@Scope("request")
public class CarController extends GenericHasCompanyIdAndDeletableController<Car,Integer> {
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
    @Value("${badRequest.car.insert.nocar}")
    private String badRequestNoCar;
    @Value("${badRequest.insert}")
    private String badRequestInsertNotAllowed;
    @Value("${badRequest.update}")
    private String badRequestUpdateNotAllowed;
    private CarRepository carRepository;
    private ManufacturerRepository manufacturerRepository;
    private ModelRepository modelRepository;
    private ReservationRepository reservationRepository;
    private ExpenseRepository expenseRepository;
    private Validator validator;
    @Autowired
    public CarController(CarRepository carRepository, ManufacturerRepository manufacturerRepository,ModelRepository modelRepository,
                         ExpenseRepository expenseRepository,ReservationRepository reservationRepository,Validator validator){
        super(carRepository);
        this.carRepository=carRepository;
        this.manufacturerRepository=manufacturerRepository;
        this.modelRepository=modelRepository;
        this.expenseRepository=expenseRepository;
        this.reservationRepository=reservationRepository;
        this.validator=validator;
    }

    //checked+
    @Override
    public List getAll(){
        return carRepository.getAllExtendedByCompanyId(userBean.getUser().getCompanyId());
    }

    @Override
    public Car insert(@RequestBody Car object) throws BadRequestException {
        throw new BadRequestException(badRequestInsertNotAllowed);
    }

    @Override
    public String update(@PathVariable Integer integer,@RequestBody Car object) throws BadRequestException {
        throw new BadRequestException(badRequestUpdateNotAllowed);
    }

    //checked+
    @RequestMapping(value = "/company/{id}",method = RequestMethod.GET)
    public List getAllExtendedByCompanyId(@PathVariable("id") Integer id){
        return carRepository.getAllExtendedByCompanyId(id);
    }

    //checked+
    @RequestMapping(value = "/location/{id}",method = RequestMethod.GET)
    public List getAllExtendedByLocationId(@PathVariable("id") Integer id){
        if(id.equals(allLocations)){
            return carRepository.getAllExtendedByCompanyId(userBean.getUser().getCompanyId());
        }
        return carRepository.getAllExtendedByLocationId(id);
    }

    //checked+
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public CarManufacturerModelFuelLocationCompany insertCarExtended(@RequestBody CarManufacturerModelFuelLocationCompany car) throws BadRequestException, ForbiddenException {
        if (car!=null) {
            validateCustomCar(car);
            Car item = new Car();
            item.setModelId(car.getModelId());
            item.setDeleted(car.getDeleted());
            item.setPlateNumber(car.getPlateNumber());
            item.setLocationId(car.getLocationId());
            item.setDeleted(car.getDeleted());
            item.setCompanyId(car.getCompanyId());
            item=carRepository.saveAndFlush(item);
            if (item != null) {
                        logCreateAction(item);
                        return carRepository.getExtendedById(item.getId());
            }
            throw new BadRequestException(badRequestInsert);
        }
        throw new BadRequestException(badRequestNoCar);
    }
    //checked+
    @RequestMapping(value = "/reservation/{startTime}/{endTime}",method = RequestMethod.GET)
    public List getFreeCars(@PathVariable("startTime") String startTime,@PathVariable("endTime") String endTime){
        return carRepository.getAllExtendedByCompanyIdAndFreeBetweenStartAndEndTime(userBean.getUser().getCompanyId(),startTime,endTime);
    }

    @RequestMapping(value = "/reservation/{startTime}/{endTime}/{id}",method = RequestMethod.GET)
    public List getFreeCarsByReservation(@PathVariable("startTime") String startTime,@PathVariable("endTime") String endTime,
                                         @PathVariable("id") Integer id){
        return carRepository.getAllExtendedByCompanyIdAndFreeBetweenStartAndEndTimeAndReservation(userBean.getUser().getCompanyId(),startTime,endTime,id);
    }
    //checked+
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/custom/{id}",method = RequestMethod.PUT)
    public CarManufacturerModelFuelLocationCompany updateExtended(@PathVariable("id") Integer id,@RequestBody CarManufacturerModelFuelLocationCompany car) throws BadRequestException, ForbiddenException {
        Car dbCar=carRepository.findById(id).orElse(null);
        if (dbCar!=null && car!=null) {
                    validateCustomCar(car);
                    Car oldCar=cloner.deepClone(dbCar);
                    if (!dbCar.getModelId().equals(car.getModelId())) {
                        dbCar.setModelId(car.getModelId());
                    }
                    if(!dbCar.getPlateNumber().equals(car.getPlateNumber())){
                        dbCar.setPlateNumber(car.getPlateNumber());
                    }
                    if(!dbCar.getDeleted().equals(car.getDeleted())){
                        dbCar.setDeleted(car.getDeleted());
                    }
                    if(!dbCar.getLocationId().equals(car.getLocationId())){
                        dbCar.setLocationId(car.getLocationId());
                    }
                    if(carRepository.saveAndFlush(dbCar)!=null){
                        logUpdateAction(dbCar,oldCar);
                        return carRepository.getExtendedById(id);
                    }
            throw new BadRequestException(badRequestUpdate);
            }
        throw new BadRequestException(badRequestNoCar);
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

    private Integer getManufacturerId(String manufacturerName) throws BadRequestException {
        if (manufacturerRepository.countAllByName(manufacturerName) == 0L) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setName(manufacturerName);
            manufacturer = manufacturerRepository.saveAndFlush(manufacturer);
            if (manufacturer == null) {
                throw new BadRequestException(badRequestInsert);
            }
            logSpecificAction(Logger.ActionType.CREATE.toString(),"Created "+manufacturer.toString(),"Manufacturer");
            return manufacturer.getId();
        }
        return manufacturerRepository.getByName(manufacturerName).getId();
    }


    private Integer getModelId(String model,String engine,String transmission,String year,Integer manufacturerId,byte[] image,Integer fuelId) throws BadRequestException {
        Model item = new Model();
        item.setEngine(engine);
        item.setFuelId(fuelId);
        item.setImage(image);
        item.setTransmission(transmission);
        item.setYear(year);
        item.setModel(model);
        item.setManufacturerId(manufacturerId);
        if (modelRepository.countAllByModelAndManufacturerId(model, manufacturerId) == 0L) {
            insertModel(item);
            return item.getId();
        }else{
            final Model target=item;
            List<Model> models=modelRepository.getAllByModel(model);
            if(models.stream().filter(m->m.equalsData(target)).count()==0){
               insertModel(item);
                return item.getId();
            }else{
                Model ret= models.stream().filter(m->m.equalsData(target)).findFirst().orElse(null);
                if(ret==null){
                    throw new BadRequestException(badRequestInsert);
                }
                if(!Arrays.equals(ret.getImage(),target.getImage())){
                    ret.setImage(image);
                    updateModel(ret);
                }
                return ret.getId();
            }
        }
    }


    private void insertModel(Model model) throws BadRequestException {
        if (modelRepository.saveAndFlush(model)== null) {
            throw new BadRequestException(badRequestInsert);
        }
        logSpecificAction(Logger.ActionType.CREATE.toString(),"Created "+model.toString(),"Model");
    }
    private void updateModel(Model model) throws BadRequestException {
        if (modelRepository.saveAndFlush(model)== null) {
            throw new BadRequestException(badRequestUpdate);
        }
        logSpecificAction(Logger.ActionType.UPDATE.toString(),"Updated "+model.toString(),"Model");
    }

    private void validateCustomCar(CarManufacturerModelFuelLocationCompany car) throws BadRequestException{
        CarReservationUtils.validate(car,validator);
        car.setManufacturerId(getManufacturerId(car.getManufacturerName()));
        car.setModelId(getModelId(car.getModel(),car.getEngine(),car.getTransmission(),car.getYear(),car.getManufacturerId(),car.getImage(),car.getFuelId()));
    }
}
