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
    private String clazz;
    private String bodyStyle;
    private String engine;
    private String transmission;
    private byte[] image;

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
    @Column(name = "class")
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
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
        Model model1 = (Model) o;
        return Objects.equals(id, model1.id) &&
                Objects.equals(manufacturer, model1.manufacturer) &&
                Objects.equals(model, model1.model) &&
                Objects.equals(clazz, model1.clazz) &&
                Objects.equals(bodyStyle, model1.bodyStyle) &&
                Objects.equals(engine, model1.engine) &&
                Objects.equals(transmission, model1.transmission) &&
                Arrays.equals(image, model1.image);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, manufacturer, model, clazz, bodyStyle, engine, transmission);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
