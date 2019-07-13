package steps.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.joda.time.LocalDate;
import pojo.BookingDates;
import pojo.CreateBookingRequest;
import pojo.CreateBookingResponse;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookingPostSteps extends BaseRequest {
    static final String BOOKING_ENDPOINT = "booking";
    static final String BOOKING_POST_BODY_KEY = "bookingBodyKey";
    public static final String BOOKING_ID_KEY = "bookingId";
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();
    private Person testUser = Fairy.create().person();

    @Step("POST to the booking endpoint using valid details")
    public void postToBookingEndpoint() throws UnirestException {
        CreateBookingRequest body = generatePostBookingBody();
        postToBookingEndpoint(body);
    }

    @Step("POST the booking endpoint using checkInDate <checkInDate>, checkOutDate <checkOutDate> and auto generated data")
    public void postToBookingUsingInvalidDates(String checkInDate, String checkOutDate) throws UnirestException {
        CreateBookingRequest body = generatePostBookingBody();
        body.getBookingDates().setCheckIn(checkInDate);
        body.getBookingDates().setCheckOut(checkOutDate);
        postToBookingEndpointUsingInvalidDetails(body);
    }

    @Step("POST the booking endpoint using firstName <firstName>, lastName <lastName> and auto generated data")
    public void postToBookingUsingInvalidNames(String firstName, String lastName) throws UnirestException {
        CreateBookingRequest body = generatePostBookingBody();
        body.setFirstName(firstName);
        body.setLastName(lastName);
        postToBookingEndpointUsingInvalidDetails(body);
    }

    @Step("POST the booking endpoint using a decimal place in the price")
    public void postToBookingUsingInvalidPrice() throws UnirestException {
        CreateBookingRequest body = generatePostBookingBody();
        body.setTotalPrice("123.46");
        postToBookingEndpoint(body);
    }

    @Step("POST the booking endpoint using <totalPrice> for the price")
    public void postToBookingUsingInvalidPrice(String totalPrice) throws UnirestException {
        CreateBookingRequest body = generatePostBookingBody();
        body.setTotalPrice(totalPrice);
        postToBookingEndpointUsingInvalidDetails(body);
    }

    @Step("POST the booking endpoint using a non-boolean value for the deposit paid field")
    public void postToBookingUsingInvalidDepositPaidBoolean() throws UnirestException {
        CreateBookingRequest body = generatePostBookingBody();
        body.setDepositPaid("NotBoolean");
        postToBookingEndpointUsingInvalidDetails(body);
    }

    @Step("POST to the booking endpoint using either null, empty or auto generated values for checkInDate <checkInDate>, checkOutDate <checkOutDate>, firstName <firstName>," +
            " lastName <lastName>, totalPrice <totalPrice>, depositPaid <depositPaid>")
    public void postToBookingUsingNullValues(String checkInDate, String checkOutDate, String firstName, String lastName, String totalPrice, String depositPaid) throws UnirestException {
        CreateBookingRequest body = generatePostBookingBody();
        if (checkInDate.equalsIgnoreCase("[null]")) {
            body.getBookingDates().setCheckIn(null);
        }
        if (checkInDate.equalsIgnoreCase("")) {
            body.getBookingDates().setCheckIn("");
        }
        if (checkOutDate.equalsIgnoreCase("[null]")) {
            body.getBookingDates().setCheckOut(null);
        }
        if (checkOutDate.equalsIgnoreCase("")) {
            body.getBookingDates().setCheckOut("");
        }
        if (firstName.equalsIgnoreCase("[null]")) {
            body.setFirstName(null);
        }
        if (firstName.equalsIgnoreCase("")) {
            body.setFirstName("");
        }
        if (lastName.equalsIgnoreCase("[null]")) {
            body.setLastName(null);
        }
        if (lastName.equalsIgnoreCase("")) {
            body.setLastName("");
        }
        if (totalPrice.equalsIgnoreCase("[null]")) {
            body.setTotalPrice(null);
        }
        if (totalPrice.equalsIgnoreCase("")) {
            body.setTotalPrice("");
        }
        if (depositPaid.equalsIgnoreCase("[null]")) {
            body.setDepositPaid(null);
        }
        if (depositPaid.equalsIgnoreCase("")) {
            body.setDepositPaid("");
        }

        postToBookingEndpointUsingInvalidDetails(body);
    }

    private CreateBookingRequest generatePostBookingBody() {
        String checkInDate = String.valueOf(LocalDate.now());
        String checkOutDate = String.valueOf(LocalDate.now());
        String totalPrice = String.valueOf(new Random().nextInt(2500));
        String depositPaid = "false";
        String firstName = testUser.firstName();
        String lastName = testUser.lastName();
        BookingDates bookingDates = BookingDates.builder()
                .checkIn(checkInDate)
                .checkOut(checkOutDate)
                .build();

        return CreateBookingRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .totalPrice(totalPrice)
                .depositPaid(depositPaid)
                .bookingDates(bookingDates)
                .build();
    }

    private void postToBookingEndpoint(CreateBookingRequest createBookingRequest) throws UnirestException {
        String body = gson.toJson(createBookingRequest);
        postRequest(BOOKING_ENDPOINT, body);
        dataStore.put(BOOKING_POST_BODY_KEY, createBookingRequest);
    }

    private void postToBookingEndpointUsingInvalidDetails(CreateBookingRequest createBookingRequest) throws UnirestException {
        String body = gson.toJson(createBookingRequest);
        postRequestReturningString(BOOKING_ENDPOINT, body);
    }

    @Step("The POST booking response contains the correct information")
    public void assertBookingPostResponse() {
        HttpResponse response = (HttpResponse) dataStore.get(HTTP_RESPONSE_KEY);
        CreateBookingResponse createBookingResponse = gson.fromJson(response.getBody().toString(), CreateBookingResponse.class);
        new BookingGetSteps().assertBookingResponseBody(createBookingResponse.getBooking());
        String bookingId = createBookingResponse.getBookingId();
        assertThat(bookingId, is(notNullValue()));
        dataStore.put(BOOKING_ID_KEY, bookingId);
    }
}
