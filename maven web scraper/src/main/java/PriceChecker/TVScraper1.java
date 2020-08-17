
package PriceChecker;

import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/** Web scraping with JSoup */
public class TVScraper1 extends Thread {
    //Specifies the interval between HTTP requests to the server in seconds.
    private int crawlDelay = 1;
    
    //Allows us to shut down our application cleanly
    volatile private boolean runThread = false;
    
    /** Scrape TVs data from the electronicworldtv.co.uk website */
    @Override
    public void run(){
        runThread = true;
        
        while(runThread){
            //Name of item that we want to scrape
            String itemName = "televisions";
        
            //Get all pages
            for(int page=1; page<3; page++){

                try {
                    //Download HTML document from website
                    Document doc = Jsoup.connect("https://www.electronicworldtv.co.uk/" + itemName + "?page=/" + page)
                            .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Task")
                            .get();

                    //Get all of the products on the page
                    Elements prods = doc.select(".productlist li"); 

                    //Work through the products
                    for(int i=0; i<prods.size(); i++){

                        //Get the product title
                        Elements title = prods.get(i).select(".product-name");

                        //Get the product price
                        Elements price = prods.get(i).select(".product-price");

                        //Get the image
                        Elements image = prods.get(i).select(".product-image a img");
                        String imageSRC = image.attr("src");

                        //Get direct URL
                        Elements url = prods.get(i).select(".product-image a");
                        String urlHREF = url.attr("href");

                        //Get the description
                        Elements description = prods.get(i).select(".product-features > *");
                        description.select("div#feat71").remove();
                        description.select("div#feat82").remove();
                        
                        //Add TVs to DB using Hibernate
                        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
                        Hibernate hibernate = (Hibernate) context.getBean("hibernate");
                        hibernate.init();
                        hibernate.addTV(title.text(), description.text(), Float.parseFloat(price.text().substring(1)), imageSRC, urlHREF);
                        hibernate.shutDown();
                        
                        try {
                            sleep(1000 * crawlDelay);
                        } catch (InterruptedException ex) {
                           System.err.println(ex.getMessage());
                        }
                    }
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }   
        } 
    }
    //Other classes can use this method to terminate the thread.
    public void stopThread(){
        runThread = false;
    }
}
