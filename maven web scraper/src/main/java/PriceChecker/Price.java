
package PriceChecker;

import java.io.Serializable;
import javax.persistence.*;

/** Represents a Price.
    Java annotation is used for the mapping.
 */
@Entity
@Table(name = "price")
public class Price implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "amount")
    private float amount;
    
    /** Empty constructor */
    public Price(){
    }
    
    //Getters
    public int getId() {
        return id;
    }
    public float getAmount() {
        return amount;
    }
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
    
    /** Returns a String representation of the Price
     * @return  */
    @Override
    public String toString(){
        String str = "Price. id: " + id + "; amount: " + amount;
        return str;
    } 
}
