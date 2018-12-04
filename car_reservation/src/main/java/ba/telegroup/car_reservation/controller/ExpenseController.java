package ba.telegroup.car_reservation.controller;
import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericDeletableController;
import ba.telegroup.car_reservation.model.Expense;
import ba.telegroup.car_reservation.model.Reservation;
import ba.telegroup.car_reservation.model.modelCustom.ExpenseCarReservationUser;
import ba.telegroup.car_reservation.repository.ExpenseRepository;
import ba.telegroup.car_reservation.repository.ReservationRepository;
import ba.telegroup.car_reservation.util.chart.ChartData;
import ba.telegroup.car_reservation.util.chart.ChartInfo;
import ba.telegroup.car_reservation.util.report.ReportData;
import ba.telegroup.car_reservation.util.report.ReportInfo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


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
    @Value("${state.finished}")
    private Integer stateFinished;
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
    @Value("${chart.type.weekly}")
    private Integer reportWeekly;
    @Value("${chart.type.monthly}")
    private Integer reportMonthly;
    @Value("${chart.type.yearly}")
    private Integer reportYearly;
    @Value("${badRequest.expense.chart}")
    private String badRequestExpenseChart;
    @Value("${badRequest.expense.report}")
    private String badRequestExpenseReport;
    @Value("${cost.service}")
    private Integer costService;
    @Value("${cost.fuel}")
    private Integer costFuel;
    @Value("${cost.other}")
    private Integer costOther;
    @Value("${report.type.all}")
    private Integer typeAll;
    @Value("${report.type.vehicle}")
    private Integer typeVehicle;
    @Value("${jasper.vehicle}")
    private String jasperVehicle;
    @Value("${jasper.all}")
    private String jasperAll;

    private final JdbcTemplate jdbcTemplate;


    private ReservationRepository reservationRepository;
    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository,ReservationRepository reservationRepository,JdbcTemplate jdbcTemplate){
        super(expenseRepository);
        this.expenseRepository=expenseRepository;
        this.reservationRepository=reservationRepository;
        this.jdbcTemplate=jdbcTemplate;
    }

    @RequestMapping(value = "/custom",method = RequestMethod.GET)
    public List<ExpenseCarReservationUser> getAllExtended() {
        return expenseRepository.getAllExtended();
    }

    @RequestMapping(value="/custom/reservation/{id}",method = RequestMethod.GET)
    public List<ExpenseCarReservationUser> getAllExtendedByReservationId(@PathVariable("id") Integer id){
        return expenseRepository.getAllExtendedByReservationId(id);
    }

    @RequestMapping(value="/car/{id}",method = RequestMethod.GET)
    public List<ExpenseCarReservationUser> getAllExtendedByCarId(@PathVariable("id") Integer id){
        return expenseRepository.getAllExtendedByCarId(id);
    }

    @Override
    public String update(@PathVariable Integer integer,@RequestBody Expense object) throws BadRequestException, ForbiddenException {
        throw new BadRequestException(badRequestUpdate);
    }


    @RequestMapping(value="/custom/{id}",method = RequestMethod.PUT)
    @Transactional(rollbackFor = Exception.class)
    public ExpenseCarReservationUser updateExtended(@PathVariable("id") Integer id,@RequestBody Expense expense) throws BadRequestException, ForbiddenException {
        if(checkExpense(expense)){
            Expense expenseDb=expenseRepository.findById(id).orElse(null);
            if(expenseDb!=null && expenseDb.getUserId().equals(expense.getUserId())){
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
        if(expense!=null && expense.getReservationId()!=null) {
            Reservation reservation=reservationRepository.findById(expense.getReservationId()).orElse(null);

            return expense != null && expense.getCarId() != null && expense.getCostId() != null && expense.getPrice() != null &&
                    expense.getReservationId() != null && expense.getDate() != null && expense.getUserId() != null &&
                    expense.getDeleted() != null && expense.getDeleted().equals(notDeleted) &&
                    reservation!=null && reservation.getUserId().equals(expense.getUserId())
                    && userBean.getUser().getId().equals(expense.getUserId()) &&
                    check(expense.getDate(),reservation.getStartTime(),reservation.getEndTime())
                    && reservation.getStateId().equals(stateFinished) && expense.getCompanyId()!=null && userBean.getUser().getCompanyId().equals(expense.getCompanyId());
        }
        return false;
    }

    private Boolean check(Date date,Date timeFrom,Date timeTo){
        if(date!=null && timeFrom!=null && timeTo!=null) {
            LocalDate localDate = convertToLocalDate(date);
            LocalDate localDateFrom = convertToLocalDate(timeFrom);
            LocalDate localDateTo = convertToLocalDate(timeTo);

            return (localDate.isAfter(localDateFrom) || localDate.isEqual(localDateFrom)) && (localDate.isBefore(localDateTo) || localDate.isEqual(localDateTo));
        }
        return false;
    }

    private LocalDate convertToLocalDate(Date date){
        if(date!=null) {
            return LocalDate.ofInstant(date.toInstant(), ZoneId.of("Europe/Belgrade"));
        }
        return null;
    }
    //charts


    @RequestMapping(value = "/chart",method = RequestMethod.POST)
    public List<ChartData> getAllByChartInfo(@RequestBody ChartInfo chartInfo) throws BadRequestException {
        if(chartInfo!=null && chartInfo.check()){
            List<Expense> data=expenseRepository.getAllExpensesByCompanyIdAndDateBetween(chartInfo.getCompanyId(),chartInfo.getDateFrom().toString(),
                    chartInfo.getDateTo().toString());
            List<ChartData> chart=new ArrayList<>();
            LocalDate start=chartInfo.getDateFrom();
            while(start.compareTo(chartInfo.getDateTo())<=0){
                final LocalDate date=start;
                final ChartData point;
                if(chartInfo.getChartType().equals(reportWeekly)) {
                    String weekNumber = getWeekNumber(start);
                    point =getPoint(chart,weekNumber);
                }else if(chartInfo.getChartType().equals(reportMonthly)) {
                    String monthNumber=start.getMonthValue()+"/"+start.getYear();
                    point = getPoint(chart,monthNumber);
                }else{
                    String yearNumber=start.getYear()+"";
                    point=getPoint(chart,yearNumber);
                }
                data.stream().filter(e -> convertToLocalDate(e.getDate()).isEqual(date)).
                            forEach(r->{
                                if (r.getCostId().equals(costFuel)){
                                        point.addFuelCost(r.getPrice());
                                }else if(r.getCostId().equals(costService)){
                                    point.addServiceCost(r.getPrice());
                                }
                                else{
                                    point.addOtherCost(r.getPrice());
                                }
                            });
                start=start.plusDays(1);
            }
            return chart;
        }
        throw new BadRequestException(badRequestExpenseChart);
    }

    private String getWeekNumber(LocalDate date){
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        return weekNumber+"/"+date.getYear();
    }

    private ChartData getPoint(List<ChartData> data,String timeUnit){
        ChartData point = data.stream().filter(c -> c.getTimeUnit().equals(timeUnit)).findFirst().orElse(null);
        if (point == null) {
            point = new ChartData(timeUnit);
            data.add(point);
        }
        return point;
    }

    @RequestMapping(value = "/report",method = RequestMethod.POST)
    public ReportData getReport(@RequestBody ReportInfo reportInfo) throws BadRequestException {
        if(reportInfo!=null && reportInfo.check()){
            String name;
            if(reportInfo.getType().equals(typeAll)){
                name=jasperAll;
            }
            else if(reportInfo.getType().equals(typeVehicle)){
                name=jasperVehicle;
            }
            else{
                throw new BadRequestException(badRequestExpenseReport);
            }
            JasperPrint jasperPrint = null;
            try {
                jasperPrint = JasperFillManager.fillReport(getClass().getResource(name).getPath(),reportInfo.getParams(), jdbcTemplate.getDataSource().getConnection());
            } catch (JRException | SQLException e) {
                e.printStackTrace();
                throw new BadRequestException(badRequestExpenseReport);
            }
            String fileName;
            if("PDF".equals(reportInfo.getFormat())){
                fileName = "Report_" +(reportInfo.getType()==1?"all":"vehicle") + "_" +reportInfo.getDateFrom()+"_"+reportInfo.getDateTo() + ".pdf";
                pdfExporterJR(jasperPrint, fileName);
            }
            else if("XLS".equals(reportInfo.getFormat())){
                fileName = "Report_" +(reportInfo.getType()==1?"all":"vehicle") + "_" +reportInfo.getDateFrom()+"_"+reportInfo.getDateTo() + ".xls";
                xlsExporterJR(jasperPrint, fileName);
            }
            else if("CSV".equals(reportInfo.getFormat())){
                fileName = "Report_" +(reportInfo.getType()==1?"all":"vehicle") + "_" +reportInfo.getDateFrom()+"_"+reportInfo.getDateTo() + ".csv";
                csvExporterJR(jasperPrint, fileName);
            }
            else{
                throw new BadRequestException(badRequestExpenseReport);
            }

            ReportData reportData = new ReportData();
            reportData.setName(fileName);
            try {
                reportData.setContent(Files.readAllBytes(new File(fileName).toPath()));
                Files.delete(new File(fileName).toPath());
            } catch (IOException e) {
                e.printStackTrace();
                throw new BadRequestException(badRequestExpenseReport);
            }
            return reportData;
        }
        throw new BadRequestException(badRequestExpenseReport);
    }

    private void pdfExporterJR(JasperPrint jasperPrint, String fileName){
        try {
            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));

            SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
            reportConfig.setSizePageToContent(true);
            reportConfig.setForceLineBreakPolicy(false);

            SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
            exportConfig.setMetadataAuthor(userBean.getUser().getUsername());
            exportConfig.setEncrypted(true);
            exportConfig.setAllowedPermissionsHint("PRINTING");

            exporter.setConfiguration(reportConfig);
            exporter.setConfiguration(exportConfig);

            exporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private void xlsExporterJR(JasperPrint jasperPrint, String fileName){
        try {
            JRXlsxExporter exporter = new JRXlsxExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));

            SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
            reportConfig.setSheetNames(new String[] {"Vehicle expenses"});

            exporter.setConfiguration(reportConfig);
            exporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private void csvExporterJR(JasperPrint jasperPrint, String fileName){
        try {
            JRCsvExporter exporter = new JRCsvExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleWriterExporterOutput(fileName));

            exporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

}
