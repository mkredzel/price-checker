package PriceChecker;

import java.io.IOException;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Unit testing.
 */
public class AppTest {

    ScraperHandler scraperHandler;
    static SessionFactory sessionFactory;
    static Session session;
    static int idTV;
    static int idComparison;

    // Session Factory Test
    @Test
    @Order(1)
    @DisplayName("Session Factory Test")
    void sessionFactoryTest() {
        Hibernate hibernate = new Hibernate();
        AppConfig app = new AppConfig();

        try {
            hibernate.setSessionFactory(app.sessionFactory());
        } catch (Exception ex) {
            fail("Error: Can't Set Session Factory: " + ex.getMessage());
        }

        assertNotNull(hibernate.getSessionFactory());
        System.out.println("Test 1 has been completed");
    }

    //Scraper List Test
    @Test
    @Order(2)
    @DisplayName("Scraper List Test")
    void scraperListTest() {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ScraperHandler handler = (ScraperHandler) context.getBean("scraperHandler");

        assertNotNull(handler);
        System.out.println("Test 2 has been completed");
    }

    //Running Threads Test
    @Test
    @Order(3)
    @DisplayName("Running Threads Test")
    void runningThreadsTest() {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ScraperHandler handler = (ScraperHandler) context.getBean("scraperHandler");

        try {
            for (Thread thread : ScraperHandler.getScraperList()) {
                thread.start();
            }
        } catch (Exception ex) {
            fail("Failed to start the threads" + ex.getMessage());
        }

        //Test for each thread if they are running
        for (Thread scraperThread : ScraperHandler.getScraperList()) {
            assertEquals(true, scraperThread.isAlive());
        }
        System.out.println("Test 3 has been completed");
    }

    @Before
    static void initAll() {
        try {
            //Create a builder for the standard service registry
            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

            //Load configuration from hibernate configuration file.
            //Here we are using a configuration file that specifies Java annotations.
            standardServiceRegistryBuilder.configure("resources/hibernate.cfg.xml");

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
    
    //Check if web scraper is scraping products Test.
    @Test
    @Order(4)
    @DisplayName("Check if web scraper is scraping products Test.")
    void deleteTVTest() throws IOException {

        //Download HTML document from website
        Document doc = Jsoup.connect("https://www.electronicworldtv.co.uk/televisions?page=/1")
                .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Task")
                .get();

        //Get all of the products on the page
        Elements prods = doc.select(".productlist li");

        //Work through the products
        for (Element prod : prods) {
            assertNotNull(prod);
        }
        System.out.println("Test 4 has been completed");
    }

    //Save TV and Comparison and check if TV and Comparison has been deleted Test
    @Test
    @Order(5)
    @DisplayName("Save TV and Comparison and check if TV and Comparison has been deleted Test")
    void saveTVTest() {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Hibernate hibernate = (Hibernate) context.getBean("hibernate");

        TV tv = new TV();
        String randomName = String.valueOf(Math.random() * 10);
        String brand = "Samsung";
        int price = 2000;

        tv.setBrand(brand);

        //Use Hibernate to save TV
        ArrayList<Integer> savedTVArray = hibernate.addTV(brand, randomName, "5", brand, brand, price, randomName);
        idTV = savedTVArray.get(0);
        idComparison = savedTVArray.get(1);

        hibernate.deleteTV(idTV);
        hibernate.deleteComparison(idComparison);

        assertEquals(Hibernate.tvDeletetionCompleted, true);
        assertEquals(Hibernate.comparisonDeletetionCompleted, true);
        hibernate.shutDown();
        
        System.out.println("Test 5 has been completed");
    }

    @After
    public void after() {
        sessionFactory.close();
    }
}
