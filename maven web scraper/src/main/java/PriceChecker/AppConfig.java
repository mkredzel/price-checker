package PriceChecker;

import org.springframework.context.annotation.*;

@Configuration
public class AppConfig {
    
    /**
     * ScraperHandler Bean
     * 
     * @return scraperManager 
     */
    @Bean
    public ScraperHandler scraperHandler(){
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
    public Hibernate hibernate(){
        Hibernate hibernate = new Hibernate();
        return hibernate;
    }
    
    /**
     * TV Bean
     * 
     * @return tv 
     */
    @Bean
    public TV tv(){
        TV tv = new TV();
        return tv;
    }
    
    /**
     * Price Bean
     * 
     * @return price 
     */
    @Bean
    public Price price(){
        Price price = new Price();
        return price;
    }
    
    /**
     * ProductType Bean
     * 
     * @return productType 
     */
    @Bean
    public ProductType productType(){
        ProductType productType = new ProductType();
        return productType;
    }
}
