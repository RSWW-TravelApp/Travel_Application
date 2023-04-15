package Events.Flights;

public class DeleteFlight {
    private final String id;

    public DeleteFlight(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
