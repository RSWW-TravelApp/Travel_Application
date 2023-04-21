package Events.Saga;

public class ValidatePaymentEvent extends SagaEvent{
    private final String paymentId;
    public ValidatePaymentEvent(Double price, String offerId, String flightId, Integer seatsNeeded, String paymentId) {
        super(price, offerId, flightId, seatsNeeded);
        this.paymentId = paymentId;
    }

    public String getPaymentId() {
        return paymentId;
    }
}
