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
public class TVScraper5 extends Thread {

    //Specifies the interval between HTTP requests to the server in seconds.
    private final int CRAWL_DELAY = 1;

    //Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    /**
     * Scrape TVs data from the amazon.co.uk website
     */
    @Override
    public void run() {
        runThread = true;

        while (runThread) {

            //Get all pages
            for (int page = 1; page < 8; page++) {
                try {
                    //Download HTML document from website
                    Document doc = Jsoup.connect("https://www.amazon.co.uk/s?k=tv&page=" + page)
                            .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Task")
                            .get();

                    //Get all of the products on the page
                    Elements prods = doc.select(".s-include-content-margin");

                    //Work through the products
                    for (int i = 0; i < prods.size(); i++) {

                        //Get the product title
                        Elements title = prods.get(i).select(".a-size-medium");

                        //Get the description
                        Elements descriptionText = prods.get(i).select(".sg-col-8-of-28 > *");

                        //Get the product price
                        Elements priceWhole = prods.get(i).select(".a-price-whole");
                        Elements priceFraction = prods.get(i).select(".a-price-fraction");

                        //Get the image
                        Elements image = prods.get(i).select(".s-image");
                        String imageSRC = image.attr("abs:srcset");

                        //Get direct URL
                        Elements url = prods.get(i).select(".sg-col-inner a");
                        String urlHREF = url.attr("abs:href");

                        String[] words = title.text().split(" ");
                        String price = priceWhole.text() + priceFraction.text();
                        String tvBrand;
                        String tvModel;
                        String screenSize;

                        if (words.length > 4) {
                            tvBrand = words[0];
                            tvModel = words[1];
                            screenSize = words[2].replace("?", "").replace("'", "").replace("\"", "").replace("”", "").replace("″", "");
                        } else {
                            tvBrand = "";
                            tvModel = "";
                            screenSize = "";
                        }

                        if (screenSize.length() == 2) {
                            screenSize += "\"";
                        }

                        if (screenSize.length() > 3 || "TV,".equals(screenSize) || "4K\"".equals(screenSize) || "400".equals(screenSize) || "TV\"".equals(screenSize) || "HD\"".equals(screenSize) || "PRO".equals(screenSize) || "(24".equals(screenSize) || "-".equals(screenSize) || "58\"".equals(tvModel) || "24\"".equals(tvModel) || "55NANO866NA".equals(tvBrand) || "RCA".equals(tvBrand) || "LED".equals(screenSize) || "100".equals(screenSize) || "HD".equals(screenSize) || "inch".equals(tvModel) || "2020".equals(tvModel) || "4K".equals(screenSize) || "led".equals(screenSize) || "".equals(tvBrand) || "UNITED".equals(tvBrand) || "Sniper".equals(tvBrand) || "OLED55BX6LA".equals(tvBrand) || "49UN73006LA".equals(tvBrand) || priceWhole.text().length() < 2 || "".equals(descriptionText.text()) || "".equals(title.text()) || "".equals(priceWhole.text()) || "".equals(priceFraction.text())) {
                            System.out.println("Unable to add record to DB - invalid data");
                        } else {
                            //Add TVs to DB using Hibernate
                            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
                            Hibernate hibernate = (Hibernate) context.getBean("hibernate");

                            hibernate.addTV(tvBrand, tvModel, screenSize, descriptionText.text(), imageSRC, Float.parseFloat(price.replace(",", "")), urlHREF);
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
