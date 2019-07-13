package pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class CreateBookingResponse {
    @SerializedName("bookingid")
    private String bookingId;

    private Booking booking;
}
