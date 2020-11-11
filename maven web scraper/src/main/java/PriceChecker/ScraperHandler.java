package PriceChecker;

public class ScraperHandler {

    //Empty constructor
    ScraperHandler() {
    }

    public void startThreads() {
        new Thread(new TVScraper1()).start();
        new Thread(new TVScraper2()).start();
        new Thread(new TVScraper3()).start();
        new Thread(new TVScraper4()).start();
        new Thread(new TVScraper5()).start();
    }

    public void joinThreads() {
        try {
            new Thread(new TVScraper1()).join();
            new Thread(new TVScraper2()).join();
            new Thread(new TVScraper3()).join();
            new Thread(new TVScraper4()).join();
            new Thread(new TVScraper5()).join();
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
