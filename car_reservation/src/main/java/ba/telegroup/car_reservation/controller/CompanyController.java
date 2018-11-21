package ba.telegroup.car_reservation.controller;
import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericDeletableController;
import ba.telegroup.car_reservation.model.Company;
import ba.telegroup.car_reservation.repository.CompanyRepository;
import ba.telegroup.car_reservation.repository.UserRepository;
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
    @Value("${deleted.not}")
    private Byte deleted;
    private final CompanyRepository companyRepository;
    private  final UserRepository userRepository;
    @Autowired
    public CompanyController(CompanyRepository companyRepository,UserRepository userRepository){
        super(companyRepository);
        this.companyRepository=companyRepository;
        this.userRepository=userRepository;
    }

    @Transactional
    @Override
    public String delete(@PathVariable Integer id) throws BadRequestException, ForbiddenException {
            if(userRepository.deleteUsersByCompanyId(id).equals(userRepository.countAllByCompanyIdAndDeleted(id,deleted))) {
                return super.delete(id);
            }
            throw new BadRequestException(badRequestDelete);
    }
}
