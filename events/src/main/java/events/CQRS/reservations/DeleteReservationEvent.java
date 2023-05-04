package events.CQRS.reservations;

public class DeleteReservationEvent{

    private final String id;
    public DeleteReservationEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}