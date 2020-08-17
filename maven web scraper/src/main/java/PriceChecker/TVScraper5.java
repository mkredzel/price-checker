package PriceChecker;

import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/** Web scraping with JSoup */
public class TVScraper5 extends Thread {
    //Specifies the interval between HTTP requests to the server in seconds. 
    private int crawlDelay = 1;
    
    //Allows us to shut down our application cleanly
    volatile private boolean runThread = false;
    
    /** Scrape TVs data from the shop.bt.com website */
    @Override
    public void run(){
        runThread = true;
        
        while(runThread){
            try {
                //Download HTML document from website
                Document doc = Jsoup.connect("https://shop.bt.com/category/electronics,tv-and-home-entertainment,televisions/11229")
                        .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Task")
                        .get();

                //Get all of the products on the page
                Elements prods = doc.select(".product-container");

                //Work through the products
                for(int i=0; i<prods.size(); i++){

                    //Get the product title
                    Elements title = prods.get(i).select(".product-heading");

                    //Get the description
                    Elements description = prods.get(i).select(".productInfo");

                    //Get the product price
                    Elements price = prods.get(i).select(".lprice");

                    //Get the image
                    Elements image = prods.get(i).select(".product-image img");
                    String imageSRC = image.attr("abs:data-src");

                    //Get direct URL
                    Elements url = prods.get(i).select(".product-image a");
                    String urlHREF = url.attr("abs:href");

                    //Add TVs to DB using Hibernate
                    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
                    Hibernate hibernate = (Hibernate) context.getBean("hibernate");
                    hibernate.init();

                    if(title.isEmpty() || price.isEmpty() || image.isEmpty() || url.isEmpty()){
                        System.out.println("missing elemets - unable to add to DB");
                    } else {
                        hibernate.addTV(title.text(), "Description not provided", Float.parseFloat(price.text().substring(1).replace(",", "")), imageSRC, urlHREF);
                        hibernate.shutDown();
                    }
                    
                    try {
                        sleep(1000 * crawlDelay);
                    } catch (InterruptedException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }   
    } 
    
    //Other classes can use this method to terminate the thread.
    public void stopThread(){
        runThread = false;
    }
}
