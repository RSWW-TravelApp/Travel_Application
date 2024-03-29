package reservation.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Document(collection = "bookings")
public class Reservation {

    @Id
    private String reservationId;

    private String userId;
    private String offerId;
    private String flightId;

    private Boolean isPaid;
    private Boolean isCancelled;
    private Double price;
    private Integer travellers;
    private String paymentId;
    private Boolean isReserved;





    public Reservation() {
    }

    public Reservation(String reservationId, String userId, String offerId, String flightId, Boolean isPaid, Boolean isCancelled, Double price, Integer travellers, String paymentId, Boolean isReserved) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.offerId = offerId;
        this.flightId = flightId;
        this.isPaid = isPaid;
        this.isCancelled = isCancelled;
        this.price = price;
        this.travellers = travellers;
        this.paymentId = paymentId;
        this.isReserved = isReserved;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", userId='" + userId + '\'' +
                ", offerId='" + offerId +
                ", flightId='" + flightId +
                ", isPaid='" + isPaid + '\'' +
                ", cancelled='" + isCancelled + '\'' +
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
    public Optional<Boolean> getIsCancelled() {
        return Optional.ofNullable(isCancelled);
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
    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
