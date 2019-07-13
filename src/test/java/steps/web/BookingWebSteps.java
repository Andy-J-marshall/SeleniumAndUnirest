package steps.web;

import PageObjects.HotelBookingForm;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Random;
import static steps.api.BookingPostSteps.BOOKING_ID_KEY;
import static utils.DriverFactory.getDriver;

public class BookingWebSteps {

    private static final String PAGE_TITLE = "Hotel booking form";
    private String deleteButtonsCssPath = "#bookings > div[id] [type=button]";
    private WebDriver driver = getDriver();
    private HotelBookingForm hotelBookingForm = PageFactory.initElements(driver, HotelBookingForm.class);
    private Person testUser = Fairy.create().person();
    private WebDriverWait wait = new WebDriverWait(driver, 5);
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();

    @Step("Navigate to the hotel booking page")
    public void navigateToHomePage() {
        driver.get(System.getenv("host"));
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE));
    }

    @Step("Create a booking using the website")
    public void createBookingWeb() {
        String price = String.valueOf(new Random().nextInt(2500));
        fillInBookingFormUsingProvidedCredentials(testUser.firstName(), testUser.lastName(), price, "false", "2019-06-09", "2019-06-09");
    }

    @Step("Attempt to create a booking using the website using invalid credentials")
    public void createBookingInvalidCredentialsWeb() {
        String invalidPrice = "abc";
        fillInBookingFormUsingProvidedCredentials(testUser.firstName(), testUser.lastName(), invalidPrice, "false", "2019-06-09", "2019-06-09");
    }

    private void fillInBookingFormUsingProvidedCredentials(String firstName, String lastName, String price, String depositPaid, String checkInDate, String checkOutDate) {
        hotelBookingForm.firstName.sendKeys(firstName);
        hotelBookingForm.lastName.sendKeys(lastName);
        hotelBookingForm.totalPrice.sendKeys(price);
        Select depositPaidDropdown = new Select(hotelBookingForm.depositPaid);
        depositPaidDropdown.selectByVisibleText(depositPaid);
        hotelBookingForm.checkInDate.sendKeys(checkInDate);
        hotelBookingForm.checkOutDate.sendKeys(checkOutDate);
        hotelBookingForm.saveButton.click();
    }

    @Step("Delete a booking using the website")
    public void deleteBookingWeb() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(deleteButtonsCssPath)));
        List<WebElement> deleteButtons = driver.findElements(By.cssSelector(deleteButtonsCssPath));
        String bookingId = (String) dataStore.get(BOOKING_ID_KEY);
        for (WebElement lastCreatedBookingId : deleteButtons) {
            if (lastCreatedBookingId.getAttribute("onclick").contains(bookingId)) {
                lastCreatedBookingId.click();
                Gauge.writeMessage("BookingId deleted: " + bookingId);
            }
        }
    }

    @Step("CLEANUP: Delete booking")
    public void deleteAllBookingsWeb() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(deleteButtonsCssPath)));
        List<WebElement> deleteButtons = driver.findElements(By.cssSelector(deleteButtonsCssPath));
        for (WebElement lastCreatedBookingId : deleteButtons) {
            lastCreatedBookingId.click();
        }
    }
}