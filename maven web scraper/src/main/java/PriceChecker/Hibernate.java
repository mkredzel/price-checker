
package PriceChecker;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/** Simple Hibernate example that uses annotation to specify the mapping between 
 *  a TV object and the tvs table in the price_comparison database. */
public class Hibernate {
    //Creates new Sessions when we need to interact with the database 
    private SessionFactory sessionFactory;
    
    /** Empty constructor */
    Hibernate() {
    }

    /** Sets up the session factory.
     *  Call this method first.  */
    public void init(){
        try {
            //Create a builder for the standard service registry
            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

            //Load configuration from hibernate configuration file.
            //Here we are using a configuration file that specifies Java annotations.
            standardServiceRegistryBuilder.configure("hibernate.cfg.xml"); 
            
            //Create the registry that will be used to build the session factory
            StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
            try {
                //Create the session factory - this is the goal of the init method.
                sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            }
            catch (Exception e) {
                    /* The registry would be destroyed by the SessionFactory, 
                        but we had trouble building the SessionFactory, so destroy it manually */
                    System.err.println("Session Factory build failed.");
                    System.err.println(e.getMessage());
                    StandardServiceRegistryBuilder.destroy( registry );
            }

            //Ouput result
            System.out.println("Session factory built.");

        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("SessionFactory creation failed." + ex);
        }
    }
    
    // Adds a new TV to the database
    public void addTV(String title, String description, float amount, String imageURL, String directURL){
        
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();
            
        if(!checkDuplicates("direct_url", directURL)){
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            TV tv = (TV) context.getBean("tv");
            Price price = (Price) context.getBean("price"); 
            ProductType productType = (ProductType) context.getBean("productType"); 
           
        
            
        
                price.setAmount(amount);
                session.save(price);
                
                productType.setTitle(title);
                productType.setDescription(description);
                session.save(productType);
                
                //Set values of TV class that we want to add
                
                tv.setImageURL(imageURL);
                tv.setDirectURL(directURL);
                tv.setPriceId(price.getId());
                tv.setProductTypeId(productType.getId());
                
            
            
            //Add TV to database
            session.save(tv);
            //Commit transaction to save it to database
            session.getTransaction().commit();

            //Close the session and release database connection
            session.close();
            System.out.println("TV added to database with ID: " + tv.getId());
        } else {
            System.out.println("duplicate");
            session.close();
        }
    }
    
    public void updateTV(int id, String title, String description, float price, String imageURL, String directURL){
        TV tv;
        
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();
           
        //Create an instance of a TV class
        tv = new TV();
        
        //Set values of Cereal class that we want to add
        tv.setId(id);
        //tv.setTitle(title);
        //tv.setPrice(price);
        tv.setImageURL(imageURL);
        tv.setDirectURL(directURL);
        
        //Start transaction
        session.beginTransaction();
        
        //Update TV in database
        session.update(tv);
        
        //Commit transaction to save it to database
        session.getTransaction().commit();
        
        //Close the session and release database connection
        session.close();

        System.out.println("TV updated in database ID: " + tv.getId());
    }
    
    public boolean checkDuplicates(String column, String data){
        
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();
        
        //Start transaction
        session.beginTransaction();
        
        List<TV> tvList = session.createQuery("from TV where " + column + " = '" + data + "'").getResultList();
        
        return tvList.size() > 0;
    }
    
    public void deleteTV(int id){
        TV tv;
        
        Session session = sessionFactory.getCurrentSession();
        
        //Create an instance of a TV class
        tv = new TV();
        
        tv.setId(id);
        
        //Start transaction
        session.beginTransaction();
        
        //Search for a TV in database that has given id
        Object persistentInstance = session.load(TV.class, id);
        
        //Delete object if we have found a match
        if(persistentInstance != null){
            session.delete(persistentInstance);
        } else {
            System.out.println("TV with id: " + id + "does not exist.");
        }
        
        //Commit transaction to save it to database
        session.getTransaction().commit();
        
        //Close the session and release database connection
        session.close();
        System.out.println("TV has been deleted from database. ID: " + tv.getId());
    }
    
    public void shutDown(){
        sessionFactory.close();
    }
}
