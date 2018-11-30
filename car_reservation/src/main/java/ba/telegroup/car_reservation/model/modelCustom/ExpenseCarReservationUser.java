package ba.telegroup.car_reservation.model.modelCustom;

import ba.telegroup.car_reservation.model.Expense;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import java.math.BigDecimal;
import java.util.Date;

@SqlResultSetMapping(name="ExpenseCarReservationUserMapping",classes = @ConstructorResult(
        targetClass = ExpenseCarReservationUser.class,columns = {
                @ColumnResult(name="id",type = Integer.class),
        @ColumnResult(name="cost_id",type=Integer.class),
        @ColumnResult(name="cost",type=String.class),
        @ColumnResult(name="user_id",type=Integer.class),
        @ColumnResult(name="username",type=String.class),
        @ColumnResult(name="full_name",type=String.class),
        @ColumnResult(name="reservation_id",type = Integer.class),
        @ColumnResult(name="price",type= BigDecimal.class),
        @ColumnResult(name="date",type = Date.class),
        @ColumnResult(name="description",type=String.class),
        @ColumnResult(name="car_id",type=Integer.class),
        @ColumnResult(name="car_model",type=String.class),
        @ColumnResult(name="deleted",type=Byte.class)
}
))


@MappedSuperclass
public class ExpenseCarReservationUser extends Expense {
    private String username;
    private String fullName;
    private String cost;
    private String carModel;

    public ExpenseCarReservationUser(Integer id,Integer costId,String cost,Integer userId,String username,
                                     String fullName,Integer reservationId,BigDecimal price,
                                     Date date,String description,Integer carId,String carModel,Byte deleted){
        super(id,costId,carId,date,price,description,deleted,userId,reservationId);

        this.cost = cost;
        this.username = username;
        this.fullName = fullName;
        this.carModel = carModel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
}
