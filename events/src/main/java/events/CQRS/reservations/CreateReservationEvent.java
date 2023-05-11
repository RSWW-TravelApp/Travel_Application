package events.CQRS.reservations;

public class CreateReservationEvent {
    private final String id;
    private final String user_id;
    private final String offer_id;
    private final String flight_id;
    private final String is_paid;
    private final String isCancelled;
    private final Double price;
    private final Integer travellers;
    private final String paymentId;
    private String isReserved;

    public CreateReservationEvent(String id, String user_id, String offer_id, String flight_id, String is_paid, String isCancelled, Double price, Integer travellers, String paymentId, String isReserved) {
        this.id = id;
        this.user_id = user_id;
        this.offer_id = offer_id;
        this.flight_id = flight_id;
        this.is_paid = is_paid;
        this.isCancelled = isCancelled;
        this.price = price;
        this.travellers = travellers;
        this.paymentId = paymentId;
        this.isReserved = isReserved;
    }

    public Double getPrice() {
        return price;
    }

    public String getIsCancelled() {
        return isCancelled;
    }

    public Integer getTravellers() {
        return travellers;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getIsReserved() {
        return isReserved;
    }

    public String getId() {
        return id;
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

    public String getIs_paid() {
        return is_paid;
    }
}
