package events.Saga;

public class BlockResourcesEvent extends SagaEvent{
    public BlockResourcesEvent(Double price, String offerId, String flightId, Integer seatsNeeded) {
        super(price, offerId, flightId, seatsNeeded);
    }
}
