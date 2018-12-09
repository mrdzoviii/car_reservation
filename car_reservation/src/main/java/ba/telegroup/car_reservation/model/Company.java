package ba.telegroup.car_reservation.model;

import ba.telegroup.car_reservation.common.interfaces.Deletable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Company implements Deletable {
    @Positive(message = "Id must be positive integer!")
    private Integer id;
    @NotNull(message = "Company name must be not null!")
    @Size(min=1,max=100,message = "Company name length must be between 1 and 100!")
    private String name;
    @NotNull(message = "Company image must be not null!")
    @Size(min=1,message = "Image size cannot be zero")
    private byte[] logo;
    @NotNull(message = "Deleted must be not null!")
    @PositiveOrZero(message = "Deleted must be positive integer or zero!")
    private Byte deleted;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "logo")
    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
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
        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logo=" + Arrays.toString(logo) +
                ", deleted=" + deleted +
                '}';
    }
}
