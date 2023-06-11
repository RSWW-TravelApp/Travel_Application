package api_gateway_test;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;


public class PurchaseStepsDefinitions {

    WebDriver webDriver;
    String url = "http://localhost:10000/";

    @Before
    public void launch_the_application_register_and_login() throws Throwable {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Paulina\\Desktop\\chrome_driver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        webDriver = new ChromeDriver(options);
        webDriver.get(url);

        // Verify home page
        String actualString = webDriver.findElement(By.cssSelector("h1")).getText();
        assertTrue(actualString.contains("Travel Application Home Page"));

        // Enter login and password
        webDriver.get(url);
        webDriver.findElement(By.id("loginInfo")).click();

        // Register the User
        webDriver.findElement(By.id("login")).sendKeys("Paulens");
        webDriver.findElement(By.id("password")).sendKeys("123");
        webDriver.findElement(By.cssSelector("button[onclick*='registerUser()']")).click();

        // Login
        webDriver.findElement(By.id("login")).sendKeys("Paulens");
        webDriver.findElement(By.id("password")).sendKeys("123");
        webDriver.findElement(By.cssSelector("button[onclick*='loginUser()']")).click();
    }

    @After
    public void user_logout_and_close_session() throws Throwable {
        webDriver.findElement(By.cssSelector("button[name='loginButton']")).click();
        webDriver.close();
    }

    @Given("^User enter departure and destination country$")
    public void enter_departure_destination_country() throws Throwable {
        webDriver.findElement(By.id("departure_country")).sendKeys("Polska");
        webDriver.findElement(By.id("arrival_country")).sendKeys("Grecja");
        webDriver.findElement(By.cssSelector("form#form button")).click();
    }

    @When("^User choose flight$")
    public void choose_flight() throws Throwable {
        TimeUnit.SECONDS.sleep(3);
        boolean flightChosen = false;

        while(!flightChosen){
            Random random = new Random();
            String flightsCounter = webDriver.findElement(By.id("flightsCounter")).getText();
            int spaceIndex = flightsCounter.indexOf(" ");
            String numberString = flightsCounter.substring(0, spaceIndex);
            int number = Integer.parseInt(numberString);
            System.out.println(number + " flights found");

            List<WebElement> webFlightsList = webDriver.findElements(By.xpath("//button[@name='flightId']"));
            int webFlightsListLength = webFlightsList.size();
            System.out.println("Number of available flights: " + webFlightsListLength);

            // Assert number of flights found equals to number of buttons with flightId
            assertEquals(number, webFlightsListLength);

            if (webFlightsListLength > 0){
                // Choose random flight from the list of all available flights on the website
                int randomIndex = random.nextInt(webFlightsList.size());
                WebElement randomFlight = webFlightsList.get(randomIndex);
                String flightId = randomFlight.getAttribute("value");
                System.out.println("Randomly chosen flightId: " + flightId);

                webDriver.findElement(By.xpath("//button[@value='" + flightId + "']")).click();
                flightChosen = true;
            } else{
                System.out.println("No flights available for these parameters " + webFlightsListLength);
                webDriver.close();
                return;
            }
        } // while
    }

    @And("^User enter Room type and Stars$")
    public void enter_room_type_and_stars() throws Throwable {
        WebElement roomTypeElement = webDriver.findElement(By.id("room_type"));
        Select roomTypeSelect = new Select(roomTypeElement);
        roomTypeSelect.selectByValue("apartment");

        WebElement starsElement = webDriver.findElement(By.id("stars"));
        Select starsSelect = new Select(starsElement);
        starsSelect.selectByValue("3");

        webDriver.findElement(By.cssSelector("form#form button")).click();
    }

    @And("^User choose offer$")
    public void choose_offer() throws Throwable {
        TimeUnit.SECONDS.sleep(3);

        while (true) {
            Random random = new Random();
            String offersCounter = webDriver.findElement(By.id("offersCounter")).getText();
            int spaceIndex = offersCounter.indexOf(" ");
            String numberString = offersCounter.substring(0, spaceIndex);
            int number = Integer.parseInt(numberString);
            System.out.println(number + " offers found");

            List<WebElement> webOffersList = webDriver.findElements(By.xpath("//button[@name='flightId']"));
            int webOffersListLength = webOffersList.size();
            System.out.println("Number of available offers: " + webOffersListLength);

            // Assert number of offers found equals to number of buttons with flightId
            assertEquals(number, webOffersListLength);

            if (webOffersListLength > 0) {
                // Choose random offer from the list of all available offers on the website
                int randomIndex = random.nextInt(webOffersList.size());
                WebElement randomOffer = webOffersList.get(randomIndex);
                String offerId = randomOffer.getAttribute("value");
                System.out.println("Randomly chosen offerId: " + offerId);

                webDriver.findElement(By.xpath("//button[@value='" + offerId + "']")).click();
                break;
            }else{
                System.out.println(webOffersListLength + " offers found");
                webDriver.findElement(By.xpath("//button[contains(text(), 'Go back')]")).click();
            }
        } // while
    }

    @And("^User Reserve the Offer$")
    public void reserve_the_offer() throws Throwable {
        webDriver.findElement(By.cssSelector("button[onclick*='reserveOffer()']")).click();
        TimeUnit.SECONDS.sleep(3);
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

    @Then("^User successfully Purchase the Offer$")
    public void successfully_purchase_the_offer() throws Throwable {
        webDriver.findElement(By.cssSelector("button[onclick*='purchaseOffer(\\'success\\')']")).click();
        TimeUnit.SECONDS.sleep(2);
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

    @Then("^User Purchase the Offer after 1 min and fail$")
    public void purchase_the_offer_after_min() throws Throwable {
        TimeUnit.SECONDS.sleep(60);
        webDriver.findElement(By.cssSelector("button[onclick*='purchaseOffer(\\'success\\')']")).click();
        TimeUnit.SECONDS.sleep(2);
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

    @Then("^User tries to Purchase the Offer$")
    public void attempt_to_purchase_the_offer() throws Throwable {
        webDriver.findElement(By.cssSelector("button[onclick*='purchaseOffer(\\'random\\')']")).click();
        TimeUnit.SECONDS.sleep(2);
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }

    @Then("^User failed to Purchase the Offer$")
    public void failed_to_purchase_the_offer() throws Throwable {
        webDriver.findElement(By.cssSelector("button[onclick*='purchaseOffer(\\'fail\\')']")).click();
        TimeUnit.SECONDS.sleep(2);
        Alert alert = webDriver.switchTo().alert();
        alert.accept();
    }






}