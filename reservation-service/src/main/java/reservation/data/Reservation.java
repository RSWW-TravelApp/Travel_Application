package reservation.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Optional;

@Document(collection = "bookings")
public class Reservation {

    @Id
    private String reservationId;

    private String userId;
    private String offerId;
    private String flightId;

    private Boolean isPaid;

    public Reservation() {
    }

    public Reservation(String reservationId, String userId, String offerId, String flightId, Boolean isPaid) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.offerId = offerId;
        this.flightId = flightId;
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", userId='" + userId + '\'' +
                ", offerId=" + offerId +
                ", flightId=" + flightId +
                ", isPaid='" + isPaid + '\'' +
                '}';
    }

    public String getReservationId() {
        return reservationId;
    }
    public Optional<String> getUserId() {
        return Optional.ofNullable(userId);
    }
    public Optional<String> getOfferId() {
        return Optional.ofNullable(offerId);
    }
    public Optional<String> getFlightId() {
        return Optional.ofNullable(flightId);
    }
    public Optional<Boolean> getIsPaid() {
        return Optional.ofNullable(isPaid);
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }
    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
