package reservationmaster.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Optional;

@Document(collection = "bookings_master")
public class Reservation{

    @Id
    private String reservationId;

    private String userId;
    private String offerId;
    private String flightId;

    private Boolean isPaid;
    private List<ReservationNested> events;

    public Reservation() {
    }

    public Reservation(String reservationId, String userId, String offerId, String flightId, Boolean isPaid,
                       List<ReservationNested> events) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.offerId = offerId;
        this.flightId = flightId;
        this.isPaid = isPaid;
        this.events = events;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", userId='" + userId + '\'' +
                ", offerId='" + offerId +
                ", flightId='" + flightId +
                ", isPaid='" + isPaid + '\'' +
                ", events='" + events + '\'' +
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
    public Optional<List<ReservationNested>> getEvents() {return Optional.ofNullable(events);}

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
    public void setEvents(List<ReservationNested> events){this.events = events;}
}
