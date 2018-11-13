package ba.telegroup.car_reservation.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mail_option", schema = "car_reservation", catalog = "")
public class MailOption {
    private Integer id;
    private String option;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "option")
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailOption that = (MailOption) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
