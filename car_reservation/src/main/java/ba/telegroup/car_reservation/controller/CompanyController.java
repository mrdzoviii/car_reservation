package ba.telegroup.car_reservation.controller;
import ba.telegroup.car_reservation.controller.genericController.GenericDeletableController;
import ba.telegroup.car_reservation.model.Company;
import ba.telegroup.car_reservation.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/company")
@Controller
@Scope("request")
public class CompanyController extends GenericDeletableController<Company,Integer> {
    private  CompanyRepository companyRepository;
    @Autowired
    public CompanyController(CompanyRepository companyRepository){
        super(companyRepository);
        this.companyRepository=companyRepository;
    }
}
