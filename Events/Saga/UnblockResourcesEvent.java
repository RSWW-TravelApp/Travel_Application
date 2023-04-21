package Events.Saga;

public class UnblockResourcesEvent extends SagaEvent{
    public UnblockResourcesEvent(Double price, String offerId, String flightId, Integer seatsNeeded) {
        super(price, offerId, flightId, seatsNeeded);
    }
}
