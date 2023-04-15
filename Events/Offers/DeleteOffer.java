package Events.Offers;

public class DeleteOffer {
    private final String id;

    public DeleteOffer(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
