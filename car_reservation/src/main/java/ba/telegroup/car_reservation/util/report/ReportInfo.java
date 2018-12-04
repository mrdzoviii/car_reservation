package ba.telegroup.car_reservation.util.report;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;



public class ReportInfo {
    private Integer typeAll=1;
    private Integer typeVehicle=2;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateFrom;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date dateTo;
    private Integer companyId;
    private Integer carId;
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



    public Boolean check(){
        if(dateFrom!=null && dateTo!=null && dateFrom.compareTo(dateTo)<=0 && companyId!=null && type!=null && format!=null){
            if(type.equals(typeVehicle)){
                return carId!=null;
            }
            return true;
        }
        return false;
    }


}
