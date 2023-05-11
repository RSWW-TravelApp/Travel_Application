package events.Saga;

public abstract class SagaEvent {
    private final Double price;
    private final String payment_id;
    private final String user_id;
    private final String offer_id;
    private final String flight_id;
    private final String reservation_id;
    private final Integer seatsNeeded;

    public SagaEvent(Double price, String user_id, String offer_id, String flight_id, String payment_id, String reservation_id, Integer seatsNeeded) {
        this.price = price;
        this.user_id = user_id;
        this.offer_id = offer_id;
        this.flight_id = flight_id;
        this.seatsNeeded = seatsNeeded;
        this.payment_id = payment_id;
        this.reservation_id = reservation_id;
    }

    public String getPayment_id() {
        return payment_id;
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

    public String getReservation_id() {
        return reservation_id;
    }

    public Integer getSeatsNeeded() {
        return seatsNeeded;
    }
}
