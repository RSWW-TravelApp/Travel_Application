package events.Saga;


public class BlockResourcesEvent extends SagaEvent{
    private Boolean success_flight = false;
    private Boolean success_offer = false;
    public BlockResourcesEvent(Double price, String user_id, String offerId, String flightId, String payment_id, String reservation_id, Integer seatsNeeded ) {
        super(price, user_id, offerId, flightId, payment_id, reservation_id, seatsNeeded);
    }

    public Boolean getSuccess_flight() {
        return success_flight;
    }

    public Boolean getSuccess_offer() {
        return success_offer;
    }

    public void setSuccess_flight(Boolean success_flight) {
        this.success_flight = success_flight;
    }

    public void setSuccess_offer(Boolean success_offer) {
        this.success_offer = success_offer;
    }
}