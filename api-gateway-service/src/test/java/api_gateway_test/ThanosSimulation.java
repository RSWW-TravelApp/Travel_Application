package api_gateway_test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThanosSimulation {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Paulina\\Desktop\\chrome_driver\\chromedriver.exe");
        String url = "http://localhost:10000/";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver webDriver = new ChromeDriver(options);

        performLogin(webDriver, url, "ThanosTheMadMan", "maslo");

        for (int i = 1; i <= 100; i++) {
            try {
                chooseRandomFlightAndOffer(webDriver, url);
                purchaseOffer(webDriver);
                webDriver.findElement(By.xpath("//button[contains(text(), 'Go back')]")).click();
                webDriver.findElement(By.xpath("//button[contains(text(), 'Go back')]")).click();
            } catch (InterruptedException e) {
                System.out.println("Either flight or offer wasn't available to purchase.");
            }
        }
        webDriver.quit();
    }

    public static void chooseRandomFlightAndOffer(WebDriver webDriver, String url) throws InterruptedException {
//        webDriver.get(url);
        TimeUnit.SECONDS.sleep(2);

        while (true) {
            Random random = new Random();
            List<WebElement> webFlightsList = webDriver.findElements(By.xpath("//button[@name='flightId']"));
            int webFlightsListLength = webFlightsList.size();
            System.out.println(webFlightsListLength + " flights found");

            if (webFlightsListLength > 0) {
                int randomIndex = random.nextInt(webFlightsList.size());
                WebElement randomFlight = webFlightsList.get(randomIndex);

                String flightId = randomFlight.getAttribute("value");
                System.out.println("flightId: " + flightId);

                TimeUnit.SECONDS.sleep(2);
                webDriver.findElement(By.cssSelector("button[name='flightId'][value='" + flightId + "']")).click();
                logSeleniumBrowserConsoleLogs(webDriver);

                TimeUnit.SECONDS.sleep(2);
                List<WebElement> webOffersList = webDriver.findElements(By.xpath("//button[@name='flightId']"));
                int webOffersListLength = webOffersList.size();
                System.out.println(webOffersListLength + " offers found");

                if (webOffersListLength > 0) {
                    int randomIndex2 = random.nextInt(webOffersList.size());
                    WebElement randomOffer = webOffersList.get(randomIndex2);

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
                TimeUnit.SECONDS.sleep(2);
                System.out.println("No flights available for these parameters " + webFlightsListLength);
//                TimeUnit.SECONDS.sleep(3);
//                webDriver.close();
//                return;
            }
        }
    }

    public static void performLogin(WebDriver webDriver, String url, String username, String password) {
        webDriver.get(url);
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

    public static void purchaseOffer(WebDriver webDriver) throws InterruptedException {

        // Reserve the offer
        webDriver.findElement(By.cssSelector("button[onclick*='reserveOffer()']")).click();
        TimeUnit.SECONDS.sleep(3);
        Alert alert = webDriver.switchTo().alert();
        alert.accept();

        // Purchase the offer
        webDriver.findElement(By.cssSelector("button[onclick*='purchaseOffer(\\'random\\')']")).click();
        TimeUnit.SECONDS.sleep(3);
        Alert alert2 = webDriver.switchTo().alert();
        alert2.accept();

        TimeUnit.SECONDS.sleep(3);
    }

    public static void logSeleniumBrowserConsoleLogs(WebDriver webDriver) {
        LogEntries logEntries = webDriver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            System.out.println("OUR Log: " + entry.getLevel() + " " + entry.getMessage());
        }
    }





}
