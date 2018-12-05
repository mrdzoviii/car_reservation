package ba.telegroup.car_reservation.controller;
import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericDeletableController;
import ba.telegroup.car_reservation.model.Company;
import ba.telegroup.car_reservation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/company")
@RestController
@Scope("request")
public class CompanyController extends GenericDeletableController<Company,Integer> {
    @Value("${badRequest.delete}")
    private String badRequestDelete;
    @Value("${deleted.yes}")
    private Byte deleted;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CarRepository carRepository;
    private final ExpenseRepository expenseRepository;
    private final ReservationRepository reservationRepository;
    @Autowired
    public CompanyController(CompanyRepository companyRepository,UserRepository userRepository,LocationRepository locationRepository,
                             CarRepository carRepository,ExpenseRepository expenseRepository,ReservationRepository reservationRepository){
        super(companyRepository);
        this.companyRepository=companyRepository;
        this.userRepository=userRepository;
        this.locationRepository=locationRepository;
        this.carRepository=carRepository;
        this.expenseRepository=expenseRepository;
        this.reservationRepository=reservationRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String delete(@PathVariable Integer id) throws BadRequestException, ForbiddenException {
            if(userRepository.deleteUsersByCompanyId(id).equals(userRepository.countAllByCompanyIdAndDeleted(id,deleted))
                    && carRepository.deleteByCompanyId(id).equals(carRepository.countAllByCompanyIdAndDeleted(id,deleted))
                    && locationRepository.deleteByCompanyId(id).equals(locationRepository.countAllByCompanyIdAndDeleted(id,deleted))
                    && expenseRepository.deleteByCompanyId(id).equals(expenseRepository.countAllByCompanyIdAndDeleted(id,deleted))
                    && reservationRepository.deleteByCompanyId(id).equals(reservationRepository.countAllByCompanyIdAndDeleted(id,deleted))
                    ) {
                return super.delete(id);
            }
            throw new BadRequestException(badRequestDelete);
    }
}
