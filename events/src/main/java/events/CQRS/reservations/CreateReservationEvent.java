package events.CQRS.reservations;

public class CreateReservationEvent {
    private final String id;
    private final String user_id;
    private final String offer_id;
    private final String flight_id;
    private final String is_paid;

    public CreateReservationEvent(String id, String user_id, String offer_id, String flight_id, String is_paid) {
        this.id = id;
        this.user_id = user_id;
        this.offer_id = offer_id;
        this.flight_id = flight_id;
        this.is_paid = is_paid;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public String getFlight_id() {
        return flight_id;
    }

    public String getIs_paid() {
        return is_paid;
    }
}
