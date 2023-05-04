package events.Saga;

public class UnblockResourcesEvent extends SagaEvent{
    public UnblockResourcesEvent(Double price, String user_id, String offerId, String flightId, Integer seatsNeeded) {
        super(price, user_id, offerId, flightId, seatsNeeded);
    }
}
