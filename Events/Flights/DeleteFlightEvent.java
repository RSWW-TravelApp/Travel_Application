package Events.Flights;

public record DeleteFlightEvent(String id) {

    public String getId() {
        return id;
    }
}
