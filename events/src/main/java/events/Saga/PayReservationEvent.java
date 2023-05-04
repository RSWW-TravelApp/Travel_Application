package events.Saga;

public class PayReservationEvent extends SagaEvent{
    public PayReservationEvent(Double price, String user_id, String offerId, String flightId, Integer seatsNeeded) {
        super(price, user_id, offerId, flightId, seatsNeeded);
    }
}
