package events.Saga;

public class ValidatePaymentEvent extends SagaEvent{

    public ValidatePaymentEvent(Double price, String user_id, String offerId, String flightId, String paymentId, String reservation_id, Integer seatsNeeded) {
        super(price, user_id, offerId, flightId, paymentId, reservation_id, seatsNeeded);

    }


}
