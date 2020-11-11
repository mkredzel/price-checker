
package PriceChecker;

import java.io.Serializable;
import javax.persistence.*;

/** Represents a ProductType.
    Java annotation is used for the mapping.
 */
@Entity
@Table(name = "product_type")
public class ProductType implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    /** Empty constructor */
    public ProductType(){
    }
    
    //Getters
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    /** Returns a String representation of the ProductType
     * @return  */
    @Override
    public String toString(){
        String str = "Price. id: " + id + "; title: " + title + "; description: " + description;
        return str;
    } 
}
