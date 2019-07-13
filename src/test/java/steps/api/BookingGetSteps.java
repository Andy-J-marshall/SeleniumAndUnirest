package steps.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import org.junit.Assert;
import pojo.Booking;
import pojo.CreateBookingRequest;
import pojo.GetAllBookings;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static steps.api.BookingPostSteps.*;


public class BookingGetSteps extends BaseRequest {
    public static final String NUMBER_OF_BOOKINGS_BEFORE_POST = "numberOfBookingsBeforePost";
    private static final String NUMBER_OF_BOOKINGS_KEY = "numberOfBookings";
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();

    @Step("GET all bookings")
    public void getBookingEndpoint() throws UnirestException {
        getRequest(BOOKING_ENDPOINT);
    }

    @Step("GET all bookings response does not contain the deleted booking")
    public void getBookingResponseDoesNotContainBookingId() {
        HttpResponse response = (HttpResponse) dataStore.get(HTTP_RESPONSE_KEY);
        GetAllBookings[] getAllBookingsResponse = gson.fromJson(response.getBody().toString(), GetAllBookings[].class);
        String bookingId = (String) dataStore.get(BOOKING_ID_KEY);

        boolean deletedBookingIdFound = false;
        for (GetAllBookings bookings : getAllBookingsResponse) {
            if (bookings.getBookingId().equalsIgnoreCase(bookingId)) {
                deletedBookingIdFound = true;
            }
        }
        if (deletedBookingIdFound) {
            Assert.fail("Deleted bookingId found in GET all bookings response. Booking was not deleted");
        }
    }

    @Step("GET booking using Id of the recently created booking")
    public void getRecentBookingId() throws UnirestException {
        String bookingId = (String) dataStore.get(BOOKING_ID_KEY);
        getBookingById(bookingId);
    }

    @Step("GET invalid booking using bookingId <bookingId>")
    public void getInvalidBookingUsingSpecifiedBookingId(String bookingId) throws UnirestException {
        String endpoint = String.format("%s/%s", BOOKING_ENDPOINT, bookingId);
        getRequestReturningString(endpoint);
    }

    @Step("GET booking using bookingId <bookingId>")
    public void getBookingUsingSpecifiedBookingId(String bookingId) throws UnirestException {
        getBookingById(bookingId);
    }

    private void getBookingById(String bookingId) throws UnirestException {
        String endpoint = String.format("%s/%s", BOOKING_ENDPOINT, bookingId);
        getRequest(endpoint);
    }

    @Step("The GET all bookings response contains a bookingId for each booking")
    public void assertGetAllBookingsResponse() {
        int numberOfBookings = returnNumberOfBookings();

        if (numberOfBookings < 1) {
            Assert.fail("No bookings found.");
        }
        dataStore.put(NUMBER_OF_BOOKINGS_KEY, numberOfBookings);
    }

    private int returnNumberOfBookings() {
        HttpResponse response = (HttpResponse) dataStore.get(HTTP_RESPONSE_KEY);
        GetAllBookings[] bookingResponse = gson.fromJson(response.getBody().toString(), GetAllBookings[].class);

        int numberOfBookings = 0;
        for (GetAllBookings booking : bookingResponse) {
            String bookingId = booking.getBookingId();
            assertThat(bookingId, is(notNullValue()));
            numberOfBookings++;
        }
        return numberOfBookings;
    }

    @Step("Do a GET to the Booking endpoint and find the current number of bookings")
    public void checkCurrentNumberOfBookings() throws UnirestException {
        getBookingEndpoint();
        int numberOfBookings = returnNumberOfBookings();
        dataStore.put(NUMBER_OF_BOOKINGS_BEFORE_POST, numberOfBookings);
    }

    @Step("Do a GET to the Booking endpoint and check the number of bookings has increased")
    public void checkBookingHasBeenAdded() throws UnirestException {
        getCurrentAndUpdatedNumberOfBookings(1);
    }

    @Step("Do a GET to the Booking endpoint and check the number of bookings has not increased")
    public void checkBookingNotAdded() throws UnirestException {
        getCurrentAndUpdatedNumberOfBookings(0);
    }

    @Step("Do a GET to the Booking endpoint and check the number of bookings has decreased")
    public void checkBookingDeleted() throws UnirestException {
        getCurrentAndUpdatedNumberOfBookings(-1);
    }

    private void getCurrentAndUpdatedNumberOfBookings(int changeInNumberOfBookings) throws UnirestException {
        getBookingEndpoint();
        int numberOfBookings = (int) dataStore.get(NUMBER_OF_BOOKINGS_BEFORE_POST);
        int updatedNumberOfBookings = returnNumberOfBookings();
        assertThat(updatedNumberOfBookings, is(equalTo(numberOfBookings + changeInNumberOfBookings)));
    }


    @Step("The GET booking response for the last created bookingId contains the correct information")
    public void assertGetBookingResponse() {
        HttpResponse response = (HttpResponse) dataStore.get(HTTP_RESPONSE_KEY);
        Booking bookingResponse = gson.fromJson(response.getBody().toString(), Booking.class);
        assertBookingResponseBody(bookingResponse);
    }

    void assertBookingResponseBody(Booking bookingResponse) {
        CreateBookingRequest bookingRequest = (CreateBookingRequest) dataStore.get(BOOKING_POST_BODY_KEY);

        String expectedCheckInDate = bookingRequest.getBookingDates().getCheckIn();
        String expectedCheckoutDate = bookingRequest.getBookingDates().getCheckOut();
        boolean expectedDepositPaidFlag = Boolean.valueOf(bookingRequest.getDepositPaid());
        String expectedFirstName = bookingRequest.getFirstName();
        String expectedLastName = bookingRequest.getLastName();
        double expectedTotalPrice = Double.valueOf(bookingRequest.getTotalPrice());

        assertThat(bookingResponse.getBookingDates().getCheckIn(), is(equalTo(expectedCheckInDate)));
        assertThat(bookingResponse.getBookingDates().getCheckOut(), is(equalTo(expectedCheckoutDate)));
        assertThat(bookingResponse.isDepositPaid(), is(equalTo(expectedDepositPaidFlag)));
        assertThat(bookingResponse.getFirstName(), is(equalTo(expectedFirstName)));
        assertThat(bookingResponse.getLastName(), is(equalTo(expectedLastName)));
        assertThat(bookingResponse.getTotalPrice(), is(equalTo(expectedTotalPrice)));
    }
}
