package payment.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Document(collection = "payments")
public class Payment {
    @Id
    private String paymentId;
    private String userId;
    private String offerId;
    private String flightId;
    private String reservationId;
    private Boolean isPaid;
    private Boolean isExpired;
    private Double price;

    @Transient
    private Integer seatsNeeded;

    public Payment(String paymentId, String userId, String offerId,
                   String flightId, Boolean isPaid, Boolean isExpired, Double price, Integer seatsNeeded) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.offerId = offerId;
        this.flightId = flightId;
        this.reservationId = null;
        this.isPaid = isPaid;
        this.isExpired = isExpired;
        this.price = price;
        this.seatsNeeded = seatsNeeded;
    }
    public Payment(){}

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                "userId='" + userId + '\'' +
                "offerId='" + offerId + '\'' +
                "flightId='" + flightId + '\'' +
                "isReserved='" + reservationId + '\'' +
                "isPaid='" + isPaid + '\'' +
                "isExpired='" + isExpired + '\'' +
                "price='" + price + '\'' +
                '}';
    }
    public Integer getSeatsNeeded() {
        return seatsNeeded;
    }
    public String getPaymentId() {
        return paymentId;
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

    public String getReservationId() {
        return reservationId;
    }
    public Boolean getIsPaid() {
        return isPaid;
    }
    public Boolean getIsExpired() {
        return isExpired;
    }
    public Double getPrice() {
        return price;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
    public void setReservationId(String reservationId) {
        reservationId = reservationId;
    }
    public void setIsExpired(Boolean expired) {
        isExpired = expired;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

}
