package ba.telegroup.car_reservation.util.chart;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class ChartInfo {
    @NotNull(message = "Date from must be not null!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateFrom;
    @NotNull(message = "Date to must be not null!")
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private LocalDate dateTo;
    @NotNull(message = "Company id must be not null!")
    @Positive(message = "Company id must be positive integer!")
    private Integer companyId;
    @NotNull(message = "Chart type must be not null!")
    @Positive(message = "Chart type must be positive integer!")
    private Integer chartType;

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getChartType() {
        return chartType;
    }

    public void setChartType(Integer chartType) {
        this.chartType = chartType;
    }

    public ChartInfo() {
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Boolean checkDate(){
        return  (dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateTo));
    }
}
