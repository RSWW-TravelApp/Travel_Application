package events.Saga;

public class TONotificationEvent extends SagaEvent{
    public TONotificationEvent(Double price, String user_id, String offer_id, String flight_id, Integer seatsNeeded) {
        super(price, user_id, offer_id, flight_id, seatsNeeded);
    }
}