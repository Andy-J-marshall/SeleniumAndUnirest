package pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Booking {
    @SerializedName("firstname")
    private String firstName;

    @SerializedName("lastname")
    private String lastName;

    @SerializedName("totalprice")
    private double totalPrice;

    @SerializedName("depositpaid")
    private boolean depositPaid;

    @SerializedName("bookingdates")
    private BookingDates bookingDates;
}
