package ba.telegroup.car_reservation.util.report;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;


public class ReportInfo {
    private Integer typeAll=1;
    private Integer typeVehicle=2;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateFrom;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date dateTo;
    private Integer companyId;
    private Integer carId;
    private String period;
    private Integer type;
    private String format;

    public Integer getTypeAll() {
        return typeAll;
    }

    public void setTypeAll(Integer typeAll) {
        this.typeAll = typeAll;
    }

    public Integer getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(Integer typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Boolean check(){
        if(dateFrom!=null && dateTo!=null && dateFrom.compareTo(dateTo)<=0 && companyId!=null && type!=null && period!=null && format!=null){
            if(type.equals(typeVehicle)){
                return carId!=null;
            }
            return true;
        }
        return false;
    }

    public HashMap getParams(){
        if(check()) {
            LocalDate from=dateToLocalDate(dateFrom);
            LocalDate to=dateToLocalDate(dateTo);
            String period=from.getMonthValue()+"/"+from.getYear()+" - "+to.getMonthValue()+"/"+to.getYear();
            HashMap params = new HashMap();
            params.put("period",period);
            System.out.println((type.equals(typeVehicle))+" type"+type+" "+typeVehicle);
            if (type.equals(typeVehicle)) {
                params.put("companyId",companyId);
                params.put("carId",carId);
                params.put("dateFrom",dateFrom);
                params.put("dateTo",dateTo);
            }else{
                params.put("companyIdIn",companyId);
                params.put("startDateIn",dateFrom);
                params.put("endDateIn",dateTo);
            }
            return params;
        }
        return null;
    }
    private LocalDate dateToLocalDate(Date date){
        return date.toLocalDate();
    }
}
