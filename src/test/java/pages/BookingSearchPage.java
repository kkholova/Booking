package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BookingSearchPage extends BasePage{

    @FindBy(id = "ss")
    private WebElement searchField;

    @FindBy(css = ".sb-submit-helper")
    private WebElement searchButton;

   public  BookingSearchPage(WebDriver driver) {
        super(driver);
    }

    public void searchByHotelName(String name){
        searchField.sendKeys(name);
        searchButton.click();
    }


}
