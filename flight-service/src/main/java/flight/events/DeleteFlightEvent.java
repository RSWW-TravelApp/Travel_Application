package flight.events;

public class DeleteFlightEvent{
    private final String id;

    public DeleteFlightEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
