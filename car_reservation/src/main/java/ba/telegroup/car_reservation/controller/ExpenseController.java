package ba.telegroup.car_reservation.controller;
import ba.telegroup.car_reservation.common.exceptions.BadRequestException;
import ba.telegroup.car_reservation.common.exceptions.ForbiddenException;
import ba.telegroup.car_reservation.controller.genericController.GenericDeletableController;
import ba.telegroup.car_reservation.model.Expense;
import ba.telegroup.car_reservation.model.Reservation;
import ba.telegroup.car_reservation.model.modelCustom.ExpenseCarReservationUser;
import ba.telegroup.car_reservation.repository.ExpenseRepository;
import ba.telegroup.car_reservation.repository.ReservationRepository;
import ba.telegroup.car_reservation.util.CarReservationUtils;
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

import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;


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
    @Value("${badRequest.expense.noexpense}")
    private String noExpense;
    @Value("${badRequest.expense.noreservation}")
    private String noReservation;
    @Value("${badRequest.expense.nodata}")
    private String noData;

    private final JdbcTemplate jdbcTemplate;
    private Validator validator;
    private ReservationRepository reservationRepository;
    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository,ReservationRepository reservationRepository,JdbcTemplate jdbcTemplate,
                             Validator validator){
        super(expenseRepository);
        this.expenseRepository=expenseRepository;
        this.reservationRepository=reservationRepository;
        this.jdbcTemplate=jdbcTemplate;
        this.validator=validator;
    }

    //checked+
    @RequestMapping(value = "/custom",method = RequestMethod.GET)
    public List getAllExtended() {
        return expenseRepository.getAllExtended();
    }
    //checked+
    @RequestMapping(value="/custom/reservation/{id}",method = RequestMethod.GET)
    public List getAllExtendedByReservationId(@PathVariable("id") Integer id){
        return expenseRepository.getAllExtendedByReservationId(id);
    }

    //checked+
    @RequestMapping(value="/car/{id}",method = RequestMethod.GET)
    public List getAllExtendedByCarId(@PathVariable("id") Integer id){
        return expenseRepository.getAllExtendedByCarId(id);
    }

    //checked+
    @Override
    public String update(@PathVariable Integer integer,@RequestBody Expense object) throws BadRequestException {
        throw new BadRequestException(badRequestUpdate);
    }

    //checked+
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

    //checked+
    @Override
    public String delete(@PathVariable Integer id) throws BadRequestException, ForbiddenException {
        Expense expense=expenseRepository.findById(id).orElse(null);
        if(expense!=null && expense.getUserId().equals(userBean.getUser().getId())) {
            return super.delete(id);
        }
        throw new BadRequestException(badRequestExpenseDelete);
    }

    //checked+
    @RequestMapping(value="/custom",method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseCarReservationUser insertExtended(@RequestBody Expense expense) throws BadRequestException, ForbiddenException {
        if(checkExpense(expense)){
            return expenseRepository.getExtendedById(super.insert(expense).getId());
        }
        throw new BadRequestException(badRequestExpenseInsert);
    }


    //checked+
    @Transactional(rollbackFor = Exception.class)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Expense insert(@RequestBody Expense expense) throws BadRequestException, ForbiddenException {
        throw new BadRequestException(badRequestInsert);
    }


    //charts


    @RequestMapping(value = "/chart",method = RequestMethod.POST)
    public List<ChartData> getAllByChartInfo(@RequestBody ChartInfo chartInfo) throws BadRequestException {
        if(chartInfo!=null){
            CarReservationUtils.validate(chartInfo,validator);
            if(chartInfo.checkDate()) {
                List<Expense> data = expenseRepository.getAllExpensesByCompanyIdAndDateBetween(chartInfo.getCompanyId(), chartInfo.getDateFrom().toString(),
                        chartInfo.getDateTo().toString());
                List<ChartData> chart = new ArrayList<>();
                LocalDate start = chartInfo.getDateFrom();
                while (start.compareTo(chartInfo.getDateTo()) <= 0) {
                    final LocalDate date = start;
                    final ChartData point;
                    if (chartInfo.getChartType().equals(reportWeekly)) {
                        String weekNumber = CarReservationUtils.getWeekNumber(start);
                        point = getPoint(chart, weekNumber);
                    } else if (chartInfo.getChartType().equals(reportMonthly)) {
                        String monthNumber = start.getMonthValue() + "/" + start.getYear();
                        point = getPoint(chart, monthNumber);
                    } else {
                        String yearNumber = start.getYear() + "";
                        point = getPoint(chart, yearNumber);
                    }
                    data.stream().filter(e -> CarReservationUtils.convertToLocalDate(e.getDate()).isEqual(date)).
                            forEach(r -> {
                                if (r.getCostId().equals(costFuel)) {
                                    point.addFuelCost(r.getPrice());
                                } else if (r.getCostId().equals(costService)) {
                                    point.addServiceCost(r.getPrice());
                                } else {
                                    point.addOtherCost(r.getPrice());
                                }
                            });
                    start = start.plusDays(1);
                }
                return chart;
            }
            throw new BadRequestException(badRequestExpenseChart);
        }
        throw new BadRequestException(noData);
    }

    @RequestMapping(value = "/report",method = RequestMethod.POST)
    public ReportData getReport(@RequestBody ReportInfo reportInfo) throws BadRequestException {
        if(reportInfo!=null){
            CarReservationUtils.validate(reportInfo,validator);
            if(reportInfo.checkReport()) {
                String name;
                if (reportInfo.getType().equals(typeAll)) {
                    name = jasperAll;
                } else if (reportInfo.getType().equals(typeVehicle)) {
                    name = jasperVehicle;
                } else {
                    throw new BadRequestException(badRequestExpenseReport);
                }
                JasperPrint jasperPrint = null;
                try {
                    jasperPrint = JasperFillManager.fillReport(getClass().getResource(name).getPath(), getParams(reportInfo), jdbcTemplate.getDataSource().getConnection());
                } catch (JRException | SQLException e) {
                    e.printStackTrace();
                    throw new BadRequestException(badRequestExpenseReport);
                }
                String fileName;
                if ("PDF".equals(reportInfo.getFormat())) {
                    fileName = "Report_" + (reportInfo.getType() == 1 ? "all" : "vehicle") + "_" + reportInfo.getDateFrom() + "_" + reportInfo.getDateTo() + ".pdf";
                    pdfExporterJR(jasperPrint, fileName);
                } else if ("XLS".equals(reportInfo.getFormat())) {
                    fileName = "Report_" + (reportInfo.getType() == 1 ? "all" : "vehicle") + "_" + reportInfo.getDateFrom() + "_" + reportInfo.getDateTo() + ".xls";
                    xlsExporterJR(jasperPrint, fileName);
                } else if ("CSV".equals(reportInfo.getFormat())) {
                    fileName = "Report_" + (reportInfo.getType() == 1 ? "all" : "vehicle") + "_" + reportInfo.getDateFrom() + "_" + reportInfo.getDateTo() + ".csv";
                    csvExporterJR(jasperPrint, fileName);
                } else {
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
        throw new BadRequestException(noData);
    }



    private ChartData getPoint(List<ChartData> data,String timeUnit){
        ChartData point = data.stream().filter(c -> c.getTimeUnit().equals(timeUnit)).findFirst().orElse(null);
        if (point == null) {
            point = new ChartData(timeUnit);
            data.add(point);
        }
        return point;
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

    HashMap getParams(ReportInfo info) throws BadRequestException {
        CarReservationUtils.validate(info,validator);
        if(info.checkReport()) {
            LocalDate from=info.getDateFrom().toLocalDate();
            LocalDate to=info.getDateTo().toLocalDate();
            String period=from.getMonthValue()+"/"+from.getYear()+" - "+to.getMonthValue()+"/"+to.getYear();
            HashMap params = new HashMap();
            params.put("period",period);
            if (info.getType().equals(typeVehicle)) {
                params.put("companyId",info.getCompanyId());
                params.put("carId",info.getCarId());
                params.put("dateFrom",info.getDateFrom());
                params.put("dateTo",info.getDateTo());
            }else{
                params.put("companyIdIn",info.getCompanyId());
                params.put("startDateIn",info.getDateFrom());
                params.put("endDateIn",info.getDateTo());
            }
            return params;
        }
        return null;
    }

    private Boolean checkExpense(Expense expense) throws BadRequestException {
        if(expense!=null) {
            CarReservationUtils.validate(expense,validator);
            Reservation reservation=reservationRepository.findById(expense.getReservationId()).orElse(null);
            if(reservation!=null) {
                return expense.getDeleted().equals(notDeleted) && reservation.getUserId().equals(expense.getUserId())
                        && userBean.getUser().getId().equals(expense.getUserId()) &&
                        checkTime(expense.getDate(), reservation.getStartTime(), reservation.getEndTime())
                        && reservation.getStateId().equals(stateFinished) && userBean.getUser().getCompanyId().equals(expense.getCompanyId());
            }
            throw new BadRequestException(noReservation);
        }
        throw new BadRequestException(noExpense);

    }

    private Boolean checkTime(Date date,Date timeFrom,Date timeTo){
        if(date!=null && timeFrom!=null && timeTo!=null) {
            LocalDate localDate = CarReservationUtils.convertToLocalDate(date);
            LocalDate localDateFrom = CarReservationUtils.convertToLocalDate(timeFrom);
            LocalDate localDateTo = CarReservationUtils.convertToLocalDate(timeTo);

            return (localDate.isAfter(localDateFrom) || localDate.isEqual(localDateFrom)) && (localDate.isBefore(localDateTo) || localDate.isEqual(localDateTo));
        }
        return false;
    }

}
