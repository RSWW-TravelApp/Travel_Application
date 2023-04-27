package events.Saga;

public class RemoveReservationEvent extends SagaEvent{
    public RemoveReservationEvent(Double price, String offerId, String flightId, Integer seatsNeeded) {
        super(price, offerId, flightId, seatsNeeded);
    }
}
