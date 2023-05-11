package events.Saga;

public class RefundPaymentEvent extends SagaEvent{
    public RefundPaymentEvent(Double price, String user_id, String offer_id, String flight_id, String payment_id, String reservation_id, Integer seatsNeeded) {
        super(price, user_id, offer_id, flight_id, payment_id, reservation_id, seatsNeeded);
    }
}
