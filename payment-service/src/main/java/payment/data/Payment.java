package payment.data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Document(collection = "payments")
public class Payment {
    private String paymentID;

    private String reservationID;

    private Boolean isPaid;

    public Payment() {
    }

    public Payment(String paymentID, String reservationID, Boolean isPaid) {
        this.paymentID = paymentID;
        this.reservationID = reservationID;
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID='" + paymentID + '\'' +
                ", reservationID='" + reservationID + '\'' +
                ", isPaid='" + isPaid + '\'' +
                '}';
    }

    public String getPaymentID() {
        return paymentID;
    }
    public Optional<String> getReservationID() {
        return Optional.ofNullable(reservationID);
    }
    public Optional<Boolean> getIsPaid() {
        return Optional.ofNullable(isPaid);
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }
    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
