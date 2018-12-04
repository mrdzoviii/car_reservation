package ba.telegroup.car_reservation.util.chart;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class ChartData {

    private BigDecimal serviceCost;
    private BigDecimal fuelCost;
    private BigDecimal otherCost;
    private String timeUnit;


    public ChartData(String timeUnit) {
        serviceCost=BigDecimal.ZERO;
        serviceCost.setScale(2);
        fuelCost=BigDecimal.ZERO;
        fuelCost.setScale(2);
        otherCost=BigDecimal.ZERO;
        otherCost.setScale(2);
        this.timeUnit=timeUnit;
    }
    public BigDecimal getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(BigDecimal serviceCost) {
        this.serviceCost = serviceCost;
    }
    public BigDecimal getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(BigDecimal fuelCost) {
        this.fuelCost = fuelCost;
    }
    public BigDecimal getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(BigDecimal otherCost) {
        this.otherCost = otherCost;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void addServiceCost(BigDecimal serviceCost){
        this.serviceCost=this.serviceCost.add(serviceCost);
        this.serviceCost=this.serviceCost.setScale(2, RoundingMode.HALF_UP);
    }
    public void addFuelCost(BigDecimal fuelCost){
        this.fuelCost=this.fuelCost.add(fuelCost);
        this.fuelCost=this.fuelCost.setScale(2, RoundingMode.HALF_UP);

    }
    public void addOtherCost(BigDecimal otherCost){
        this.otherCost=this.otherCost.add(otherCost);
        this.otherCost=this.otherCost.setScale(2, RoundingMode.HALF_UP);
       
    }
}
