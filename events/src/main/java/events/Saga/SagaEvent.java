package events.Saga;

public abstract class SagaEvent {
    private final Double price;
    private final String user_id;
    private final String offer_id;
    private final String flight_id;
    private final Integer seatsNeeded;

    public SagaEvent(Double price, String user_id, String offer_id, String flight_id, Integer seatsNeeded) {
        this.price = price;
        this.user_id = user_id;
        this.offer_id = offer_id;
        this.flight_id = flight_id;
        this.seatsNeeded = seatsNeeded;
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

    public Double getPrice() {
        return price;
    }

    public String getOfferId() {
        return offer_id;
    }

    public String getFlightId() {
        return flight_id;
    }

    public Integer getSeatsNeeded() {
        return seatsNeeded;
    }
}
