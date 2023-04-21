package Events.Saga;

public class PayReservationEvent extends SagaEvent{
    public PayReservationEvent(Double price, String offerId, String flightId, Integer seatsNeeded) {
        super(price, offerId, flightId, seatsNeeded);
    }
}
