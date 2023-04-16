package reservation.data;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Optional;


@ToString
//@AllArgsConstructor
//@NoArgsConstructor
@Data
@Document(collection = "reservations")
public class Reservation {

    @Field("_id")
    //@JsonProperty("id")
    @Id
    private String reservationID;

    private String userID;
    private String hotelID;
    private String flightID;

    private boolean isPaid;


    public Optional<String> getUserID() {
        return Optional.ofNullable(userID);
    }
    public Optional<String> getHotelID() {
        return Optional.ofNullable(hotelID);
    }
    public Optional<String> getFlightID() {
        return Optional.ofNullable(flightID);
    }
    public Optional<Boolean> getIsPaid() {
        return Optional.ofNullable(isPaid);
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }
    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }
    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}
