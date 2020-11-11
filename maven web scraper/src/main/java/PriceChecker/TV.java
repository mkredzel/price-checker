package PriceChecker;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Represents a TV. Java annotation is used for the mapping.
 */
@Entity
@Table(name = "tv")
public class TV implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "screen_size")
    private String screenSize;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageURL;

    /**
     * Empty constructor
     */
    public TV() {
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Returns a String representation of the TV
     *
     * @return
     */
    @Override
    public String toString() {
        String str = "TV. id: " + id + "; brand: " + brand + "; model: " + model + "; screen size: " + screenSize + "; description: " + description + "image URL: " + imageURL;
        return str;
    }
}
