package steps.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import org.junit.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static steps.api.BookingPostSteps.BOOKING_ID_KEY;

public class BookingDeleteSteps extends BaseRequest {

    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();

    @Step("DELETE a booking")
    public void deleteRecentBooking() throws UnirestException {
        String bookingId = (String) dataStore.get(BOOKING_ID_KEY);
        deleteBooking(bookingId);
    }

    @Step("DELETE a booking using bookingId <bookingId>")
    public void deleteBookingUsingBookingId(String bookingId) throws UnirestException {
        deleteBooking(bookingId);
    }

    @Step("DELETE a booking using no auth header")
    public void deleteBookingUsingBookingId() throws UnirestException {
        String endpoint = deleteBookingEndpoint("12345");
        deleteRequestNoAuth(endpoint);
    }

    private void deleteBooking(String bookingId) throws UnirestException {
        String endpoint = deleteBookingEndpoint(bookingId);
        deleteRequest(endpoint);
    }

    @Step("CLEANUP: DELETE the last created booking")
    public void deleteLastCreatedBooking() throws UnirestException {
        String bookingId = (String) dataStore.get(BOOKING_ID_KEY);
        deleteBooking(bookingId);
        HttpResponse response = (HttpResponse) dataStore.get(HTTP_RESPONSE_KEY);
        Integer statusCode = response.getStatus();
        if (!statusCode.equals(405) & !statusCode.equals(201)) {
            Gauge.writeMessage("Clean up step was not successful. Booking was not deleted");
            Assert.fail();
        }
        Gauge.writeMessage("Booking deleted successfully");
    }

    @Step("Assert Created appears in response body after successfully deleting a booking")
    public void createdAppearsInDeleteResponseBody() {
        HttpResponse response = (HttpResponse) dataStore.get(HTTP_RESPONSE_KEY);
        assertThat(response.getBody().toString(), containsString("Created"));
    }

    private String deleteBookingEndpoint(String bookingId) {
        return "booking/" + bookingId;
    }
}
