package ba.telegroup.car_reservation.controller;
import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericDeletableController;
import ba.telegroup.car_reservation.model.Expense;
import ba.telegroup.car_reservation.model.Reservation;
import ba.telegroup.car_reservation.model.modelCustom.ExpenseCarReservationUser;
import ba.telegroup.car_reservation.repository.ExpenseRepository;
import ba.telegroup.car_reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("api/expense")
@Scope("request")
public class ExpenseController extends GenericDeletableController<Expense,Integer> {
    private ExpenseRepository expenseRepository;
    @Value("${badRequest.delete}")
    private String badRequestDelete;
    @Value("${badRequest.update}")
    private String badRequestUpdate;
    @Value("${action.success}")
    private String actionSuccess;
    @Value("${badRequest.insert}")
    private String badRequestInsert;
    @Value("${deleted.not}")
    private Byte notDeleted;
    @Value("${badRequest.expense.insert}")
    private String badRequestExpenseInsert;
    @Value("${badRequest.expense.update}")
    private String badRequestExpenseUpdate;
    @Value("${badRequest.expense.delete}")
    private String badRequestExpenseDelete;
    private ReservationRepository reservationRepository;
    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository,ReservationRepository reservationRepository){
        super(expenseRepository);
        this.expenseRepository=expenseRepository;
        this.reservationRepository=reservationRepository;
    }

    @RequestMapping(value = "/custom",method = RequestMethod.GET)
    public List<ExpenseCarReservationUser> getAllExtended() {
        return expenseRepository.getAllExtended();
    }

    @RequestMapping(value="/custom/reservation/{id}",method = RequestMethod.GET)
    public List<ExpenseCarReservationUser> getAllExtendedByReservationId(@PathVariable("id") Integer id){
        return expenseRepository.getAllExtendedByReservationId(id);
    }

    @Override
    public String update(@PathVariable Integer integer,@RequestBody Expense object) throws BadRequestException, ForbiddenException {
        throw new BadRequestException(badRequestUpdate);
    }


    @RequestMapping(value="/custom/{id}",method = RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public ExpenseCarReservationUser updateExtended(@PathVariable("id") Integer id,@RequestBody Expense expense) throws BadRequestException, ForbiddenException {
        if(checkExpense(expense)){
            Reservation reservation=reservationRepository.findById(expense.getReservationId()).orElse(null);
            Expense expenseDb=expenseRepository.findById(id).orElse(null);
            if(expenseDb!=null && expenseDb.getUserId().equals(expense.getUserId()) &&
                    reservation!=null && reservation.getUserId().equals(expense.getUserId())
                    && userBean.getUser().getId().equals(expense.getUserId()) && check(expense.getDate(),reservation.getStartTime(),reservation.getEndTime())){
                if(super.update(id,expense).equals(actionSuccess)){
                    return expenseRepository.getExtendedById(id);
                }
            }

        }
        throw new BadRequestException(badRequestExpenseUpdate);
    }

    @Override
    public String delete(@PathVariable Integer id) throws BadRequestException, ForbiddenException {
        Expense expense=expenseRepository.findById(id).orElse(null);
        if(expense!=null && expense.getUserId().equals(userBean.getUser().getId())) {
            return super.delete(id);
        }
        throw new BadRequestException(badRequestExpenseDelete);
    }

    @RequestMapping(value="/custom",method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseCarReservationUser insertExtended(@RequestBody Expense expense) throws BadRequestException, ForbiddenException {
        if(checkExpense(expense)){

            Reservation reservation=reservationRepository.findById(expense.getReservationId()).orElse(null);
            System.out.println(check(expense.getDate(),reservation.getStartTime(),reservation.getEndTime()));
            if(reservation!=null && reservation.getUserId().equals(expense.getUserId())
                    && userBean.getUser().getId().equals(expense.getUserId()) && check(expense.getDate(),reservation.getStartTime(),reservation.getEndTime()))
            return expenseRepository.getExtendedById(super.insert(expense).getId());
        }
        throw new BadRequestException(badRequestExpenseInsert);
    }


    @Transactional(rollbackFor = Exception.class)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Expense insert(@RequestBody Expense expense) throws BadRequestException, ForbiddenException {
        throw new BadRequestException(badRequestInsert);
    }

    private Boolean checkExpense(Expense expense){
        return expense!=null && expense.getCarId()!=null && expense.getCostId()!=null && expense.getPrice()!=null &&
                expense.getReservationId()!=null && expense.getDate()!=null && expense.getUserId()!=null &&
                expense.getDeleted()!=null && expense.getDeleted().equals(notDeleted);
    }

    private Boolean check(Date date,Date timeFrom,Date timeTo){
        LocalDate localDate=LocalDate.ofInstant(date.toInstant(),ZoneId.of("Europe/Belgrade"));
        LocalDate localDateFrom=LocalDate.ofInstant(timeFrom.toInstant(),ZoneId.of("Europe/Belgrade"));
        LocalDate localDateTo=LocalDate.ofInstant(timeTo.toInstant(),ZoneId.of("Europe/Belgrade"));

        return (localDate.isAfter(localDateFrom) || localDate.isEqual(localDateFrom)) && (localDate.isBefore(localDateTo) || localDate.isEqual(localDateTo));
    }
}
