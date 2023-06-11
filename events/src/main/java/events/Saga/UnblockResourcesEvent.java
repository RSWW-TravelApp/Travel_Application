package events.Saga;


public class UnblockResourcesEvent extends SagaEvent{
    Boolean expiry;
    public UnblockResourcesEvent(Double price, String user_id, String offerId, String flightId, String payment_id, String reservation_id, Integer seatsNeeded) {
        super(price, user_id, offerId, flightId, payment_id, reservation_id, seatsNeeded);
        expiry = false;
    }

    public void setExpiry(Boolean expiry) {
        this.expiry = expiry;
    }

    public Boolean getExpiry() {
        return expiry;
    }
}
