package events.Saga;

public class RemoveReservationEvent extends SagaEvent{
    public RemoveReservationEvent(Double price, String user_id, String offerId, String flightId, Integer seatsNeeded) {
        super(price, user_id, offerId, flightId, seatsNeeded);
    }
}
