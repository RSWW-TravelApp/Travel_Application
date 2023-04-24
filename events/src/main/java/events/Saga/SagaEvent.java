package events.Saga;

public class SagaEvent {
    private final Double price;
    private final String offer_id;
    private final String flight_id;
    private final Integer seatsNeeded;

    public SagaEvent(Double price, String offer_id, String flight_id, Integer seatsNeeded) {
        this.price = price;
        this.offer_id = offer_id;
        this.flight_id = flight_id;
        this.seatsNeeded = seatsNeeded;
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
