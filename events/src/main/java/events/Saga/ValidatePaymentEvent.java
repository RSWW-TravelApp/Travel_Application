package events.Saga;

public class ValidatePaymentEvent extends SagaEvent{
    private final String paymentId;
    public ValidatePaymentEvent(Double price, String user_id, String offerId, String flightId, Integer seatsNeeded, String paymentId) {
        super(price, user_id, offerId, flightId, seatsNeeded);
        this.paymentId = paymentId;
    }

    public String getPaymentId() {
        return paymentId;
    }
}
