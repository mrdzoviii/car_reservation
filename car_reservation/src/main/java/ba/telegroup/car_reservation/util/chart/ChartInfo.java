package ba.telegroup.car_reservation.util.chart;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ChartInfo {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateFrom;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private LocalDate dateTo;
    private Integer companyId;
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

    public Boolean check(){
        return companyId!=null && dateFrom!=null && dateTo!=null && (dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateTo)) && chartType!=null;
    }
}
