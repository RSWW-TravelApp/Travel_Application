package events.CQRS.reservations;

import java.util.Optional;

public class UpdateReservationEvent {
    private final String id;
    private final String user_id;
    private final String offer_id;
    private final String flight_id;
    private final String is_paid;

    public UpdateReservationEvent(String id, String user_id, String offer_id, String flight_id, String is_paid) {
        this.id = id;
        this.user_id = user_id;
        this.offer_id = offer_id;
        this.flight_id = flight_id;
        this.is_paid = is_paid;
    }

    public String getId() {
        return id;
    }

    public Optional<String> getUser_id() {
        return Optional.ofNullable(user_id);
    }

    public Optional<String> getOffer_id() {
        return Optional.ofNullable(offer_id);
    }

    public Optional<String> getFlight_id() {
        return Optional.ofNullable(flight_id);
    }

    public Optional<String> getIs_paid() {
        return Optional.ofNullable(is_paid);
    }
}
