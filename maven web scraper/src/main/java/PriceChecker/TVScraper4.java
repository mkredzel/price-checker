package PriceChecker;

import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Web scraping with JSoup
 */
public class TVScraper4 extends Thread {

    //Specifies the interval between HTTP requests to the server in seconds.
    private final int CRAWL_DELAY = 1;

    //Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    /**
     * Scrape TVs data from the appliancesdirect.co.uk website
     */
    @Override
    public void run() {
        runThread = true;

        while (runThread) {

            //Get all pages
            for (int page = 1; page < 26; page++) {
                try {
                    //Download HTML document from website
                    Document doc = Jsoup.connect("https://www.appliancesdirect.co.uk/ct/tv-and-home-entertainment/tvs?pageNumber=" + page)
                            .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Task")
                            .get();

                    //Get all of the products on the page
                    Elements prods = doc.select(".OfferBox");

                    //Work through the products
                    for (int i = 0; i < prods.size(); i++) {

                        //Get the product title
                        Elements title = prods.get(i).select(".OfferBoxTitle");

                        //Get the description
                        Elements descriptionText = prods.get(i).select(".productInfo");

                        //Get the product price
                        Elements priceText = prods.get(i).select(".offerprice");

                        //Get the image
                        Elements image = prods.get(i).select(".sr_image img");
                        String imageSRC = image.attr("abs:data-original");

                        //Get direct URL
                        Elements url = prods.get(i).select(".OfferBoxTitle a");
                        String urlHREF = url.attr("abs:href");

                        String[] words = title.text().split(" ");

                        String tvBrand = words[0];
                        String tvModel = words[1];
                        String screenSize = words[2];
                        String price = priceText.text().substring(1);
                        String description = descriptionText.text().replaceFirst("\\w+\\s", "").replaceFirst("\\w+\\s", "").replaceFirst("\\w+\\s", "");
                        int a = description.indexOf(" ");
                        description = description.substring(a + 1).replaceFirst("\\w+\\s", "");

                        if (screenSize.length() == 2) {
                            screenSize += "\"";
                        }

                        if ("".equals(descriptionText.text()) || "HD\"".equals(screenSize) || "HD\"".equals(screenSize) || "Full".equals(screenSize) || "Inch".equals(screenSize)) {
                            System.out.println("Unable to add record to DB - invalid data");
                        } else {
                            //Add TVs to DB using Hibernate
                            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
                            Hibernate hibernate = (Hibernate) context.getBean("hibernate");

                            hibernate.addTV(tvBrand, tvModel, screenSize, description, imageSRC, Float.parseFloat(price.replace(",", "")), urlHREF);
                            hibernate.shutDown();
                        }

                        try {
                            sleep(1000 * CRAWL_DELAY);
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
    public void stopThread() {
        runThread = false;
    }
}
