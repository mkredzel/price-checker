
package PriceChecker;

import java.io.Serializable;
import javax.persistence.*;

/** Represents a TV.
    Java annotation is used for the mapping. 
*/
@Entity
@Table(name = "tvs")
public class TV implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "product_type_id")
    private int productTypeId;
    
    @Column(name = "price_id")
    private int priceId;
    
    @Column(name = "image_url")
    private String imageURL;
    
    @Column(name = "direct_url")
    private String directURL;
    
    /** Empty constructor */
    public TV(){
    }
    
    //Getters
    public int getId() {
        return id;
    }
    public int getProductTypeId() {
        return productTypeId;
    }
    public float getPriceId() {
        return priceId;
    }
    public String getImageURL() {
        return imageURL;
    }
    public String getDirectURL() {
        return directURL;
    }
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }
    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public void setDirectURL(String directURL) {
        this.directURL = directURL;
    }
        
    /** Returns a String representation of the TV
     * @return  */
    @Override
    public String toString(){
        String str = "TV. id: " + id + "; productTypeId: " + productTypeId + "; priceId: " + priceId + "; imageURL: " + imageURL + "; directURL: " + directURL;
        return str;
    } 
}
