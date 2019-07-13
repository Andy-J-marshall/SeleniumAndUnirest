package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HotelBookingForm {
    @FindBy(id = "firstname")
    public WebElement firstName;

    @FindBy(id = "lastname")
    public WebElement lastName;

    @FindBy(id = "totalprice")
    public WebElement totalPrice;

    @FindBy(id = "depositpaid")
    public WebElement depositPaid;

    @FindBy(id = "checkin")
    public WebElement checkInDate;

    @FindBy(id = "checkout")
    public WebElement checkOutDate;

    @FindBy(css = "#form input[type=button]")
    public WebElement saveButton;
}
