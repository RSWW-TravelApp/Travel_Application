package Events.Offers;

public record DeleteOfferEvent(String id) {

    public String getId() {
        return id;
    }
}
