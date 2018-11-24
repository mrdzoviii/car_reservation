package ba.telegroup.car_reservation.controller;

import ba.telegroup.car_reservation.model.modelCustom.LoggerUserCompany;
import ba.telegroup.car_reservation.repository.LoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/logger")
@Scope("request")
public class LoggerController {
    private LoggerRepository loggerRepository;
    @Autowired
    public LoggerController(LoggerRepository loggerRepository){
        this.loggerRepository=loggerRepository;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public List<LoggerUserCompany> getAll() {
        return loggerRepository.getAllExtended();
    }

}
