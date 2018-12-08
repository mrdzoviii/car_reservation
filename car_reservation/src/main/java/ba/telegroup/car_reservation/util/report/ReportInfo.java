package ba.telegroup.car_reservation.util.report;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.sql.Date;



public class ReportInfo {
    private Integer typeAll=1;
    private Integer typeVehicle=2;

    @NotNull(message = "Date from must be not null!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateFrom;
    @NotNull(message = "Date to must be not null!")
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date dateTo;
    @NotNull(message = "Company id must be not null!")
    @Positive(message = "Company id must be positive integer!")
    private Integer companyId;
    @Positive(message = "Car id must be positive integer!")
    private Integer carId;
    @NotNull(message = "Type must be not null!")
    @Positive(message = "Type must be positive integer!")
    private Integer type;
    @NotNull(message = "Format must be not null!")
    @Pattern(regexp = "(PDF|CSV|XLS)",message = "Format can be PDF,CSV,XLS")
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



    public Boolean checkReport(){
        if( dateFrom.compareTo(dateTo)<=0){
            if(type.equals(typeVehicle)){
                return carId!=null;
            }
            return true;
        }
        return false;
    }


}
