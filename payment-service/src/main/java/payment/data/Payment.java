package payment.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Document(collection = "payments")
public class Payment {
    @Id
    private String paymentId;

    private String reservationId;

    private Boolean isPaid;

    public Payment() {
    }

    public Payment(String paymentId, String reservationId, Boolean isPaid) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", reservationId='" + reservationId + '\'' +
                ", isPaid='" + isPaid + '\'' +
                '}';
    }

    public String getPaymentId() {
        return paymentId;
    }
    public Optional<String> getReservationId() {
        return Optional.ofNullable(reservationId);
    }
    public Optional<Boolean> getIsPaid() {
        return Optional.ofNullable(isPaid);
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
