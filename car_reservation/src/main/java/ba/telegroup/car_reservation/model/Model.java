package ba.telegroup.car_reservation.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Model {
    private Integer id;
    private Integer manufacturerId;
    private String model;
    private String engine;
    private String transmission;
    private String year;
    private Integer fuelId;
    private byte[] image;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "manufacturer_id")
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Basic
    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }





    @Basic
    @Column(name = "engine")
    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @Basic
    @Column(name = "transmission")
    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    @Basic
    @Column(name = "year")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Basic
    @Column(name = "fuel_id")
    public Integer getFuelId() {
        return fuelId;
    }

    public void setFuelId(Integer fuelId) {
        this.fuelId = fuelId;
    }

    @Basic
    @Column(name = "image")
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(id, model.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public boolean equalsData(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(getEngine(), model.getEngine()) &&
                Objects.equals(getTransmission(), model.getTransmission()) &&
                Objects.equals(getYear(), model.getYear()) &&
                Objects.equals(getFuelId(), model.getFuelId());
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", manufacturerId=" + manufacturerId +
                ", model='" + model + '\'' +
                ", engine='" + engine + '\'' +
                ", transmission='" + transmission + '\'' +
                ", year='" + year + '\'' +
                ", fuelId=" + fuelId +
                '}';
    }
}
