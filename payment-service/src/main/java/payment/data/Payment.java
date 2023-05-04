package payment.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Document(collection = "payments")
public class Payment {
    @Id
    private String paymentId;
    private String userId;
    private String reservationId;
    private String offerId;
    private String flightId;
    private Boolean isReserved;
    private Boolean isPaid;
    private Boolean isExpired;
    private Double price;

    public Payment(String paymentId, String userId, String reservationId, String offerId,
                   String flightId, Boolean isReserved, Boolean isPaid, Boolean isExpired, Double price) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.reservationId = reservationId;
        this.offerId = offerId;
        this.flightId = flightId;
        this.isReserved = isReserved;
        this.isPaid = isPaid;
        this.isExpired = isExpired;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                "userId='" + userId + '\'' +
                "reservationId='" + reservationId + '\'' +
                "offerId='" + offerId + '\'' +
                "flightId='" + flightId + '\'' +
                "isReserved='" + isReserved + '\'' +
                "isPaid='" + isPaid + '\'' +
                "isExpired='" + isExpired + '\'' +
                "price='" + price + '\'' +
                '}';
    }

    public String getPaymentId() {
        return paymentId;
    }
    public String getUserId() {
        return userId;
    }
    public String getReservationId() {
        return reservationId;
    }
    public String getOfferId() {
        return offerId;
    }
    public String getFlightId() {
        return flightId;
    }

    public Boolean getIsReserved() {
        return isReserved;
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
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
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
    public void setIsReserved(Boolean reserved) {
        isReserved = reserved;
    }
    public void setIsExpired(Boolean expired) {
        isExpired = expired;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

}
