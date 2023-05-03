package events.Saga;

public class BlockResourcesEvent extends SagaEvent{
    public BlockResourcesEvent(Double price, String user_id, String offerId, String flightId, Integer seatsNeeded) {
        super(price, user_id, offerId, flightId, seatsNeeded);
    }
}
