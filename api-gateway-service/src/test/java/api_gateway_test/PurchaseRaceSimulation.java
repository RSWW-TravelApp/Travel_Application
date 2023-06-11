package api_gateway_test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PurchaseRaceSimulation {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Paulina\\Desktop\\chrome_driver\\chromedriver.exe");
        String url = "http://localhost:10000/";

        int numUsers = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(numUsers);

        for (int i = 1; i <= numUsers; i++) {
            String username = "userThread" + i;
            String password = "password" + i;
            executorService.submit(() -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                WebDriver webDriver = new ChromeDriver(options);

                try {
                    chooseRandomFlightAndOffer(webDriver, url);
                    purchaseOffer(webDriver, username, password);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Either flight or offer wasn't available to purchase so the application ended.");
                }

//                webDriver.quit();
            });
        }
        executorService.shutdown();
    }


    public static void chooseRandomFlightAndOffer(WebDriver webDriver, String url) throws InterruptedException {
        /*
        Take list of all available flights and offers from the website, if they exist, choose one on position with index
        then try to reserve and make a purchase.
         */
        webDriver.get(url);
        TimeUnit.SECONDS.sleep(3);

        while(true) {
            List<WebElement> webFlightsList = webDriver.findElements(By.xpath("//button[@name='flightId']"));
            int webFlightsListLength = webFlightsList.size();
            System.out.println(webFlightsListLength + " flights found");

            if (webFlightsListLength > 0) {
                int index = 1;
                WebElement randomFlight = webFlightsList.get(index);

                String flightId = randomFlight.getAttribute("value");
                System.out.println("flightId: " + flightId);

                TimeUnit.SECONDS.sleep(3);
                webDriver.findElement(By.cssSelector("button[name='flightId'][value='" + flightId + "']")).click();
                logSeleniumBrowserConsoleLogs(webDriver);

                TimeUnit.SECONDS.sleep(3);
                List<WebElement> webOffersList = webDriver.findElements(By.xpath("//button[@name='flightId']"));
                int webOffersListLength = webOffersList.size();
                System.out.println(webOffersListLength + " offers found");

                if (webOffersListLength > 0) {
                    WebElement randomOffer = webOffersList.get(index);

                    String offerId = randomOffer.getAttribute("value");
                    System.out.println("offerId: " + offerId);
                    webDriver.findElement(By.cssSelector("button[name='flightId'][value='" + offerId + "']")).click();
                    logSeleniumBrowserConsoleLogs(webDriver);
                    break;

                } else {
                    System.out.println("No offers available for these parameters " + webOffersListLength);
                    webDriver.findElement(By.xpath("//button[contains(text(), 'Go back')]")).click();
                    logSeleniumBrowserConsoleLogs(webDriver);
                }

            } else {
                System.out.println("No flights available for these parameters " + webFlightsListLength);
                TimeUnit.SECONDS.sleep(3);
                webDriver.close();
                return;
            }
        } // while
    }

    public static void performLogin(WebDriver webDriver, String username, String password) {
        webDriver.findElement(By.id("loginInfo")).click();

        // Register the User
        webDriver.findElement(By.id("login")).sendKeys(username);
        webDriver.findElement(By.id("password")).sendKeys(password);
        webDriver.findElement(By.cssSelector("button[onclick*='registerUser()']")).click();

        // Login
        webDriver.findElement(By.id("login")).sendKeys(username);
        webDriver.findElement(By.id("password")).sendKeys(password);
        webDriver.findElement(By.cssSelector("button[onclick*='loginUser()']")).click();
    }

    public static void purchaseOffer(WebDriver webDriver, String username, String password) throws InterruptedException {
        performLogin(webDriver, username, password);

        // Reserve the offer
        webDriver.findElement(By.cssSelector("button[onclick*='reserveOffer()']")).click();
        TimeUnit.SECONDS.sleep(3);
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
        System.out.println(" Reservation success for " + username + "! ");

        // Purchase the offer
        webDriver.findElement(By.cssSelector("button[onclick*='purchaseOffer(\\'success\\')']")).click();
        TimeUnit.SECONDS.sleep(2);
        Alert alert2 = webDriver.switchTo().alert();
        alert2.accept();
        System.out.println(" Purchase success! " + username + "! ");

        TimeUnit.SECONDS.sleep(5);

    }

    public static void logSeleniumBrowserConsoleLogs(WebDriver webDriver) {
        LogEntries logEntries = webDriver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            System.out.println("OUR Log: " + entry.getLevel() + " " + entry.getMessage());
        }
    }

}
