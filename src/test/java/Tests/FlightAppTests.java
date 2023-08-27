package Tests;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FlightAppTests {
    private WebDriver driver;


    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://flights-app.pages.dev/");

    }

    @Test
    void FlightAppTestsSameCity() {
        String city = "Paris";  // Select a city.

        WebElement fromInput = driver.findElement(By.id("headlessui-combobox-input-:Rq9lla:"));
        fromInput.sendKeys(city);

        WebElement toInput = driver.findElement(By.id("headlessui-combobox-input-:Rqhlla:"));
        toInput.sendKeys(city);

        String from = fromInput.getAttribute("city");
        String to = toInput.getAttribute("city");

        Assert.assertNotEquals("Error: The city you write in the from and to part cannot be the same.", from, to);
    }

    @Test
    void ListingFlights() {
        String fromCity = "Istanbul";  // Select a city for from input.
        String toCity = "Los Angeles"; // Select a city for to input.

        WebElement fromInput = driver.findElement(By.xpath("//input[@id='headlessui-combobox-input-:Rq9lla:']"));
        fromInput.sendKeys(fromCity);
        fromInput.sendKeys(Keys.ENTER);

        WebElement toInput = driver.findElement(By.xpath("//input[@id='headlessui-combobox-input-:Rqhlla:']"));
        toInput.sendKeys(toCity);
        toInput.sendKeys(Keys.ENTER);

        By expectedElementLocator = By.xpath("//p[@class='mb-10']");

        // Specify the timeout as an integer (seconds)
        int timeoutSeconds = 5;  // Change the timeout as needed


        Duration timeoutDuration = Duration.ofSeconds(timeoutSeconds);

        WebDriverWait wait = new WebDriverWait(driver, timeoutDuration);

        try {
            WebElement flight = wait.until(ExpectedConditions.visibilityOfElementLocated(expectedElementLocator));
            System.out.println("Flight tickets found!");
        } catch (Exception e) {
            System.out.println("Flight tickets not found!");
        }

        WebElement element = driver.findElement(By.xpath("//p[@class='mb-10']"));
        String text = element.getText();

        int expectedFlightCount = extractexpectedFlightCount(text);
        System.out.println("Found " + expectedFlightCount + " ticket.");

        List<WebElement> flightListItems = driver.findElements(By.tagName("li"));

        int actualFlightCount = flightListItems.size();

        Assert.assertEquals("Flight count does not match the expected count.", expectedFlightCount, actualFlightCount);
    }

    public static int extractexpectedFlightCount(String text) {
        String[] words = text.split(" ");
        for (String word : words) {
            if (word.matches("\\d+")) {
                return Integer.parseInt(word);
            }
        }
        return 0;
    }

     @AfterEach
     void teardown(){
     driver.close();
     }
}
