package events.Saga;

public class RequirePaymentEvent extends SagaEvent{
    public RequirePaymentEvent(Double price, String user_id, String offerId, String flightId, Integer seatsNeeded) {
        super(price, user_id, offerId, flightId, seatsNeeded);
    }
}
