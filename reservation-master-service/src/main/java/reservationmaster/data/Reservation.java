package reservationmaster.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Document(collection = "bookings_master")
public class Reservation{

    @Id
    private String reservationId;
    private String userId;
    private String offerId;
    private String flightId;
    private String paymentId;
    private Double price;
    private Boolean isPaid;
    private Boolean isCancelled;
    private List<ReservationNested> events;
    private Integer travellers;
    private Boolean isReserved;
    public Reservation() {
    }

    public Reservation(String reservationId, String userId, String offerId, String flightId, String paymentId, Boolean isPaid,
                       Boolean isCancelled, Integer travellers,Double price, Boolean reserved) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.offerId = offerId;
        this.flightId = flightId;
        this.isPaid = isPaid;
        this.events = new ArrayList<>();
        this.isCancelled = isCancelled;
        this.travellers = travellers;
        this.price = price;
        this.paymentId = paymentId;
        this.isReserved = reserved;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", userId='" + userId + '\'' +
                ", offerId='" + offerId + '\'' +
                ", flightId='" + flightId + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", travellers='" + travellers + '\'' +
                ", price='" + price + '\'' +
                ", isPaid='" + isPaid + '\'' +
                ", events='" + events + '\'' +
                ", cancelled='" + isCancelled + '\'' +
                ", isReserved='" + isReserved + '\'' +
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
    public Boolean getIsPaid() {
        return isPaid;
    }
    public Boolean getIsCancelled() {
        return isCancelled;
    }
    public Double getPrice() {
        return price;
    }
    public Integer getTravellers() {
        return travellers;
    }

    public Boolean getReserved() {
        return isReserved;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public List<ReservationNested> getEvents() {
        return events;
    }

    public void setReserved(Boolean reserved) {
        isReserved = reserved;
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
    public void setEvents(List<ReservationNested> events){this.events = events;}
    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
    public void setTravellers(Integer travellers) {
        this.travellers = travellers;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
}
