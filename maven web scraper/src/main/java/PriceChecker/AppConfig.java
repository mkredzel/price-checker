package PriceChecker;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.*;

@Configuration
public class AppConfig {

    SessionFactory sessionFactory;

    /**
     * ScraperHandler Bean
     *
     * @return scraperManager
     */
    @Bean
    public ScraperHandler scraperHandler() {
        ScraperHandler scraperHandler = new ScraperHandler();

        // Return Scraper Handler object
        return scraperHandler;
    }

    /**
     * Hibernate Bean
     *
     * @return hibernate
     */
    @Bean
    public Hibernate hibernate() {
        Hibernate hibernate = new Hibernate();
        hibernate.setSessionFactory(sessionFactory());
        return hibernate;
    }

    /**
     * SessionFactory Bean
     *
     * @return sessionFactory
     */
    @Bean
    public SessionFactory sessionFactory() {
        if (sessionFactory == null) {//Build sessionFatory once only
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
                    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
                } catch (Exception e) {
                    /* The registry would be destroyed by the SessionFactory, 
                            but we had trouble building the SessionFactory, so destroy it manually */
                    System.err.println("Session Factory build failed.");
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                //Ouput result
                System.out.println("Session factory built.");
            } catch (Throwable ex) {
                // Make sure you log the exception, as it might be swallowed
                System.err.println("SessionFactory creation failed." + ex);
            }
        }
        return sessionFactory;
    }
}
