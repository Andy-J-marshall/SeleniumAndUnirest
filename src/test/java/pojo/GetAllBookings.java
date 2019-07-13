package pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class GetAllBookings {
    @SerializedName("bookingid")
    private String bookingId;
}
