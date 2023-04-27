package events.CQRS.offers;

public class DeleteOfferEvent{
    private final String id;

    public DeleteOfferEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
