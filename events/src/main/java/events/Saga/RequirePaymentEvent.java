package events.Saga;

public class RequirePaymentEvent extends SagaEvent{
    public RequirePaymentEvent(Double price, String offerId, String flightId, Integer seatsNeeded) {
        super(price, offerId, flightId, seatsNeeded);
    }
}
