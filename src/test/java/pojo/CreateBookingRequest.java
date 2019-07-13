package pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateBookingRequest {
    @SerializedName("firstname")
    private String firstName;

    @SerializedName("lastname")
    private String lastName;

    @SerializedName("totalprice")
    private String totalPrice;

    @SerializedName("depositpaid")
    private String depositPaid;

    @SerializedName("bookingdates")
    private BookingDates bookingDates;
}
