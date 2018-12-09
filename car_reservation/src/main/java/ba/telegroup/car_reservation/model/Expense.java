package ba.telegroup.car_reservation.model;

import ba.telegroup.car_reservation.common.interfaces.Deletable;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@SqlResultSetMapping(name = "ExpenseMapping",classes = @ConstructorResult(
        targetClass = Expense.class,columns = {
                @ColumnResult(name="id",type = Integer.class),
        @ColumnResult(name="cost_id",type=Integer.class),
        @ColumnResult(name="car_id",type=Integer.class),
        @ColumnResult(name="date",type=Date.class),
        @ColumnResult(name="price",type = BigDecimal.class),
        @ColumnResult(name="description",type=String.class),
        @ColumnResult(name="deleted",type=Byte.class),
        @ColumnResult(name="user_id",type=Integer.class),
        @ColumnResult(name="reservation_id",type=Integer.class),
        @ColumnResult(name="company_id",type=Integer.class)
}
))



@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Expense implements Deletable {
    @Positive(message = "Id must be positive integer!")
    private Integer id;
    @NotNull(message = "Cost id must be not null!")
    @Positive(message = "Cost id must be positive integer!")
    private Integer costId;
    @NotNull(message = "Cost id must be not null!")
    @Positive(message = "Cost id must be positive integer!")
    private Integer carId;
    @NotNull(message = "Date must be not null!")
    @PastOrPresent(message = "Date must be from past or present!")
    private Date date;
    @NotNull(message = "Price cannot be null!")
    @Positive(message = "Price must be positive!")
    private BigDecimal price;
    private String description;
    @NotNull(message = "Deleted must be not null!")
    @PositiveOrZero(message = "Deleted must be positive integer or zero!")
    private Byte deleted;
    @NotNull(message = "User  id must be not null!")
    @Positive(message = "User id must be positive integer!")
    private Integer userId;
    @NotNull(message = "Reservation id must be not null!")
    @Positive(message = "Reservation id must be positive integer!")
    private Integer reservationId;
    @NotNull(message = "Company id must be not null!")
    @Positive(message = "Company id must be positive integer!")
    private Integer companyId;



    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "user_id")
    public Integer getUserId(){
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "company_id")
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }




    @Column(name = "reservation_id")
    public Integer getReservationId(){
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }


    @Basic
    @Column(name = "cost_id")
    public Integer getCostId() {
        return costId;
    }

    public void setCostId(Integer costId) {
        this.costId = costId;
    }

    @Basic
    @Column(name = "car_id")
    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    @Basic
    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Europe/Belgrade")
    public Date getDate() {
        return date ;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "deleted")
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense that = (Expense) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Expense() {
    }

    public Expense(Integer id, Integer costId, Integer carId, Date date, BigDecimal price, String description, Byte deleted, Integer userId, Integer reservationId,Integer companyId) {
        this.id = id;
        this.costId = costId;
        this.carId = carId;
        this.date = date;
        this.price = price;
        this.description = description;
        this.deleted = deleted;
        this.userId = userId;
        this.reservationId = reservationId;
        this.companyId=companyId;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", costId=" + costId +
                ", carId=" + carId +
                ", date=" + date +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                ", userId=" + userId +
                ", reservationId=" + reservationId +
                ", companyId=" + companyId +
                '}';
    }
}
