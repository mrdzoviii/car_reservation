package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.common.ReadOnlyController;
import ba.telegroup.car_reservation.model.MailOption;
import ba.telegroup.car_reservation.repository.MailOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("request")
@RequestMapping("api/mail-option")
public class MailOptionController extends ReadOnlyController<MailOption,Integer> {
    @Autowired
    public MailOptionController(MailOptionRepository mailOptionRepository){
        super(mailOptionRepository);
    }
}
