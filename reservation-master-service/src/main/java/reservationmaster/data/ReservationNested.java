package reservationmaster.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Optional;

@Document(collection = "bookings_nested")
public class ReservationNested{

    @Id
    private String reservationId;
    private String userId;
    private String offerId;
    private String flightId;
    private String paymentId;
    private Double price;
    private Boolean isPaid;
    private Boolean isCancelled;
    private Integer travellers;
    private Boolean isReserved;
    private String eventType;

    public ReservationNested() {
    }

    public ReservationNested(String reservationId, String userId, String flightId, String offerId, String paymentId, Double price, Boolean isPaid, Boolean isCancelled, Integer travellers, Boolean isReserved, String eventType) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.offerId = offerId;
        this.flightId = flightId;
        this.paymentId = paymentId;
        this.price = price;
        this.isPaid = isPaid;
        this.isCancelled = isCancelled;
        this.travellers = travellers;
        this.isReserved = isReserved;
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", userId='" + userId + '\'' +
                ", offerId='" + offerId +
                ", flightId='" + flightId +
                ", isPaid='" + isPaid + '\'' +
                ", eventType='" + eventType + '\'' +
                '}';
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public Integer getTravellers() {
        return travellers;
    }

    public Boolean getReserved() {
        return isReserved;
    }

    public String getEventType() {
        return eventType;
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
    public void setEventType(String eventType){this.eventType = eventType;}
}

