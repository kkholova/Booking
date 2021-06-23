package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import pages.BookingSearchPage;

public class BookingSearchSteps {
    private static final String URL = "https://www.booking.com/searchresults.en-gb.html";
    private BookingSearchPage searchPage;
//    private static final String RATE_LOCATOR =
    String hotelName;
    String foundHotelName;
    String hotelRate;
    WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.get(URL);

    }

    @Given("Hotel name {string}")
    public void hotelName(String name) {
        hotelName = name;
    }

    @When("User does search")
    public void userDoesSearch() {
        try{
            searchPage = new BookingSearchPage(driver);
            searchPage.searchByHotelName(hotelName);
        }catch (ElementNotInteractableException e){

            WebElement element = driver.findElement(By.cssSelector(".sb-submit-helper"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
        }

    }

    @Then("Result page will contain {string} with rate {string}")
    public void resultPageWillContainWithRate(String name, String score) {
        Actions action = new Actions(driver);
        action.moveByOffset(0, 0).click().build().perform();
        foundHotelName = driver.findElement(By.xpath(String.format("//span[contains(text(),'%s')]", name))).getText();
        Assert.assertEquals(foundHotelName, name, "Not found");
        hotelRate = driver.findElement(By.xpath(String.format("//span[contains(text(),'%s')]//ancestor::div[@class = 'sr_property_block_main_row']//div[@class = 'bui-review-score__badge']", name))).getText();
        Assert.assertEquals(hotelRate, score);
    }

//    @Then("Result page will contain {string}")
//    public void resultPageWillContain(String name) {
//        Actions action = new Actions(driver);
//        action.moveByOffset(0, 0).click().build().perform();
//        foundHotelName = driver.findElement(By.xpath(String.format("//span[contains(text(),'%s')]", name))).getText();
//        Assert.assertEquals(foundHotelName, name, "Not found");
//
//    }
//
//    @And("Rate of the hotel will be {string}")
//    public void rateOfTheHotelWillBe(String score) {
//        hotelRate = driver.findElement(By.xpath(String.format("//span[contains(text(),'%s')]//ancestor::div[@class = 'sr_property_block_main_row']//div[@class = 'bui-review-score__badge']", name))).getText();
//        Assert.assertEquals(hotelRate, score);
//
//    }

    @After
    public void tearDown() {
        driver.quit();
    }



}
