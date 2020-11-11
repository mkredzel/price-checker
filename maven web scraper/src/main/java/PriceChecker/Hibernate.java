package PriceChecker;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Simple Hibernate example that uses annotation to specify the mapping between
 * a TV object and the tvs table in the price_comparison database.
 */
public class Hibernate {

    //Creates new Sessions when we need to interact with the database 
    private SessionFactory sessionFactory;

    /**
     * Empty constructor
     */
    Hibernate() {
    }

    // Adds a new TV to the database
    public void addTV(String brand, String model, String screenSize, String description, String imageURL, float price, String url) {

        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if (!checkTVDuplicates("brand", brand, "model", model)) {

            TV tv = new TV();
            Comparison comparison = new Comparison();

            //Set values of TV class that we want to add
            tv.setBrand(brand);
            tv.setModel(model);
            tv.setScreenSize(screenSize);
            tv.setDescription(description);
            tv.setImageURL(imageURL);

            //Add TV to database
            session.save(tv);

            comparison.setTvId(tv.getId());
            comparison.setPrice(price);
            comparison.setUrl(url);
            session.save(comparison);

            //Commit transaction to save it to database
            session.getTransaction().commit();

            //Close the session and release database connection
            session.close();
            System.out.println("TV added to database with ID: " + tv.getId());
        } else if (checkTVDuplicates("description", description, "image_url", imageURL)) {
            session.close();
            System.out.println("TV is already in DB");

        } else if (checkComparisonDuplicates("url", url)) {
            session.close();
            System.out.println("Comparison is already in DB");

        } else {
            //Get a new Session instance from the session factory      
            TV existingTV = matchTV("model", model);

            Comparison comparison = new Comparison();

            //Add Comparison to database
            comparison.setTvId(existingTV.getId());
            comparison.setPrice(price);
            comparison.setUrl(url);
            session.save(comparison);

            //Commit transaction to save it to database
            session.getTransaction().commit();

            session.close();
            System.out.println("New Comparison added to database with ID: " + comparison.getId());
        }
    }

    public boolean checkTVDuplicates(String column1, String data1, String column2, String data2) {
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();

        List<TV> tvList = session.createQuery("from TV where " + column1 + " = '" + data1 + "' AND " + column2 + "= '" + data2 + "'").getResultList();

        return tvList.size() > 0;
    }

    public boolean checkComparisonDuplicates(String column1, String data1) {
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();

        List<Comparison> comparisonList = session.createQuery("from Comparison where " + column1 + " = '" + data1 + "'").getResultList();

        return comparisonList.size() > 0;
    }

    public TV matchTV(String column1, String data1) {
        //Get a new Session instance from the session factory
        Session session = sessionFactory.getCurrentSession();

        List<TV> tvList = session.createQuery("from TV where " + column1 + " = '" + data1 + "'").getResultList();

        return tvList.get(0);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void shutDown() {
        sessionFactory.close();
    }
}
