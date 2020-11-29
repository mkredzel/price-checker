package PriceChecker;

import java.util.ArrayList;
import java.util.List;
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
     * @return scraperHandler
     */
    @Bean
    public ScraperHandler scraperHandler() {
        ScraperHandler scraperHandler = new ScraperHandler();

        List<Thread> scraperList = new ArrayList();
        scraperList.add(scraper1());
        scraperList.add(scraper2());
        scraperList.add(scraper3());
        scraperList.add(scraper4());
        scraperList.add(scraper5());
        ScraperHandler.setScraperList(scraperList);

        // Return Scraper Handler object
        return scraperHandler;
    }

    /**
     * TVScraper1 Bean
     *
     * @return scraper1
     */
    @Bean
    public TVScraper1 scraper1() {
        TVScraper1 scraper1 = new TVScraper1();
        return scraper1;
    }

    /**
     * TVScraper2 Bean
     *
     * @return scraper2
     */
    @Bean
    public TVScraper2 scraper2() {
        TVScraper2 scraper2 = new TVScraper2();
        return scraper2;
    }

    /**
     * TVScraper3 Bean
     *
     * @return scraper3
     */
    @Bean
    public TVScraper3 scraper3() {
        TVScraper3 scraper3 = new TVScraper3();
        return scraper3;
    }

    /**
     * TVScraper4 Bean
     *
     * @return scraper4
     */
    @Bean
    public TVScraper4 scraper4() {
        TVScraper4 scraper4 = new TVScraper4();
        return scraper4;
    }

    /**
     * TVScraper5 Bean
     *
     * @return scraper5
     */
    @Bean
    public TVScraper5 scraper5() {
        TVScraper5 scraper5 = new TVScraper5();
        return scraper5;
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
