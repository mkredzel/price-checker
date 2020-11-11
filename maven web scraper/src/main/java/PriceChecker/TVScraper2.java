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
public class TVScraper2 extends Thread {

    //Specifies the interval between HTTP requests to the server in seconds.
    private final int CRAWL_DELAY = 1;

    //Allows us to shut down our application cleanly
    volatile private boolean runThread = false;

    /**
     * Scrape TVs data from the hughes.co.uk website
     */
    @Override
    public void run() {
        runThread = true;

        while (runThread) {

            //Get all pages
            for (int page = 1; page < 17; page++) {
                try {
                    //Download HTML document from website
                    Document doc = Jsoup.connect("https://www.hughes.co.uk/tv-and-entertainment/televisions/all-televisions/?p=" + page)
                            .userAgent("Google Chrome - Computer Science Student - Webscraping Coursework Task")
                            .get();

                    //Get all of the products on the page
                    Elements prods = doc.select(".product--info");

                    //Work through the products
                    for (int i = 0; i < prods.size(); i++) {

                        //Get the product title
                        Elements title = prods.get(i).select(".product--title");

                        //Get the product price
                        Elements priceText = prods.get(i).select(".product--price .price--default");

                        //Get the image
                        Elements image = prods.get(i).select(".image--media img");
                        String imageSRC = image.attr("srcset");

                        //Get direct URL
                        Elements url = prods.get(i).select(".product--image a");
                        String urlHREF = url.attr("href");

                        String[] words = title.text().split(" ");

                        String tvBrand = words[0];
                        String tvModel;
                        String screenSize;
                        String description;

                        if ("Sony".equals(tvBrand)) {
                            String titleText = title.text().replaceFirst("\\w+\\s", "").replaceFirst("\\w+\\s", "").replaceFirst("\\w+\\s", "");
                            int a = titleText.indexOf(" ");

                            description = titleText.substring(a + 1);
                            tvModel = words[2];
                            screenSize = words[3];
                        } else {
                            String titleText = title.text().replaceFirst("\\w+\\s", "").replaceFirst("\\w+\\s", "");
                            int a = titleText.indexOf(" ");

                            description = titleText.substring(a + 1);
                            tvModel = words[1];
                            screenSize = words[2];
                        }

                        if (screenSize.length() == 2) {
                            screenSize += "\"";
                        }

                        String price = priceText.text().substring(1);

                        if ("Full".equals(screenSize) || "The".equals(screenSize) || "4K\"".equals(screenSize) || "Frame".equals(screenSize) || "Series".equals(screenSize) || screenSize.length() > 3) {
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
