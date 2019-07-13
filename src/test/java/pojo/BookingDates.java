package pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BookingDates {
    @SerializedName("checkin")
    private String checkIn;

    @SerializedName("checkout")
    private String checkOut;
}
