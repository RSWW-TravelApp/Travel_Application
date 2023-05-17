package events.Saga;

import java.io.Serializable;

public class MakeReservationEvent extends SagaEvent {

    private final String is_paid;


    public MakeReservationEvent(Double price, String offerId, String flightId, Integer seatsNeeded, String user_id,  String payment_id, String reservation_id, String is_paid) {
        super(price, user_id, offerId, flightId, payment_id, reservation_id, seatsNeeded);
        this.is_paid = is_paid;
    }


    public String isIs_paid() {
        return is_paid;
    }

    public String getIs_paid() {
        return is_paid;
    }

}
