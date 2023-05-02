package events.Saga;

public class TONotificationEvent extends SagaEvent{
    public TONotificationEvent(Double price, String offer_id, String flight_id, Integer seatsNeeded) {
        super(price, offer_id, flight_id, seatsNeeded);
    }
}
