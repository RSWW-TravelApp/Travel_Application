package events.Saga;

public class PayReservationEvent extends SagaEvent{
    public PayReservationEvent(Double price, String user_id, String offerId, String flightId, String payment_id, String reservation_id,Integer seatsNeeded) {
        super(price, user_id, offerId, flightId, payment_id, reservation_id, seatsNeeded);
    }
}
