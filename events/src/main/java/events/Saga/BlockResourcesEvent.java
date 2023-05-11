package events.Saga;


public class BlockResourcesEvent extends SagaEvent{
    private Boolean success = false;
    public BlockResourcesEvent(Double price, String user_id, String offerId, String flightId, String payment_id, String reservation_id, Integer seatsNeeded ) {
        super(price, user_id, offerId, flightId, payment_id, reservation_id, seatsNeeded);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}