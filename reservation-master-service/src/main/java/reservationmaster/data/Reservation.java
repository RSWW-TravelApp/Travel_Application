package reservationmaster.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Document(collection = "reservations")
public class Reservation {
    @Id
    private String reservationId;

    private String userId;
    private String hotelId;
    private String flightId;

    private Boolean isPaid;

    public Reservation() {
    }

    public Reservation(String reservationID, String userID, String hotelID, String flightID, Boolean isPaid) {
        this.reservationId = reservationID;
        this.userId = userID;
        this.hotelId = hotelID;
        this.flightId = flightID;
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationID='" + reservationId + '\'' +
                ", userID='" + userId + '\'' +
                ", hotelID=" + hotelId +
                ", flightID=" + flightId +
                ", isPaid='" + isPaid + '\'' +
                '}';
    }

    public String getReservationId() {
        return reservationId;
    }
    public Optional<String> getUserId() {
        return Optional.ofNullable(userId);
    }
    public Optional<String> getHotelId() {
        return Optional.ofNullable(hotelId);
    }
    public Optional<String> getFlightId() {
        return Optional.ofNullable(flightId);
    }
    public Optional<Boolean> getIsPaid() {
        return Optional.ofNullable(isPaid);
    }

    public void setReservationId(String reservationID) {
        this.reservationId = reservationID;
    }
    public void setUserId(String userID) {
        this.userId = userID;
    }
    public void setHotelId(String hotelID) {
        this.hotelId = hotelID;
    }
    public void setFlightId(String flightID) {
        this.flightId = flightID;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
