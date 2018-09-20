package ba.telegroup.car_reservation.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Model {
    private Integer id;
    private Integer manufacturer;
    private String model;
    private String type;
    private String bodyStyle;
    private String engine;
    private String transmission;
    private String year;
    private String fuel;
    private byte[] image;
    private Byte deleted;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "manufacturer")
    public Integer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Integer manufacturer) {
        this.manufacturer = manufacturer;
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
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "body_style")
    public String getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(String bodyStyle) {
        this.bodyStyle = bodyStyle;
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
    @Column(name = "fuel")
    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    @Basic
    @Column(name = "image")
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
        Model model1 = (Model) o;
        return Objects.equals(id, model1.id) &&
                Objects.equals(manufacturer, model1.manufacturer) &&
                Objects.equals(model, model1.model) &&
                Objects.equals(type, model1.type) &&
                Objects.equals(bodyStyle, model1.bodyStyle) &&
                Objects.equals(engine, model1.engine) &&
                Objects.equals(transmission, model1.transmission) &&
                Objects.equals(year, model1.year) &&
                Objects.equals(fuel, model1.fuel) &&
                Arrays.equals(image, model1.image) &&
                Objects.equals(deleted, model1.deleted);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, manufacturer, model, type, bodyStyle, engine, transmission, year, fuel, deleted);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
