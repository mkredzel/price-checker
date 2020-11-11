package PriceChecker;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Represents a ProductType. Java annotation is used for the mapping.
 */
@Entity
@Table(name = "comparison")
public class Comparison implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tv_id")
    private int tvId;

    @Column(name = "price")
    private float price;

    @Column(name = "url")
    private String url;

    /**
     * Empty constructor
     */
    public Comparison() {
    }

    //Getters
    public int getId() {
        return id;
    }

    public int getTvId() {
        return tvId;
    }

    public float getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTvId(int tvId) {
        this.tvId = tvId;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns a String representation of the Comparison
     *
     * @return
     */
    @Override
    public String toString() {
        String str = "Comparison. id: " + id + "; tv id: " + tvId + "; price: " + price + "; url: " + url;
        return str;
    }
}
