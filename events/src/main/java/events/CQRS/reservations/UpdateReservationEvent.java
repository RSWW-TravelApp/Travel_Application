package events.CQRS.reservations;

import java.util.Optional;

public class UpdateReservationEvent {
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

    public UpdateReservationEvent(String id, String user_id, String offer_id, String flight_id, String is_paid, String isCancelled, Double price, Integer travellers, String paymentId, String isReserved) {
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

    public String getId() {
        return id;
    }

    public Optional<String> getUser_id() {
        return Optional.ofNullable(user_id);
    }

    public Optional<String> getOffer_id() {
        return Optional.ofNullable(offer_id);
    }

    public Optional<String> getFlight_id() {
        return Optional.ofNullable(flight_id);
    }

    public Optional<String> getIs_paid() {
        return Optional.ofNullable(is_paid);
    }

    public Optional<String> getIsCancelled() {
        return Optional.ofNullable(isCancelled);
    }

    public Optional<Double> getPrice() {
        return Optional.ofNullable(price);
    }

    public Optional<Integer> getTravellers() {
        return Optional.ofNullable(travellers);
    }

    public Optional<String> getPaymentId() {
        return Optional.ofNullable(paymentId);
    }

    public Optional<String> getIsReserved() {
        return Optional.ofNullable(isReserved);
    }
}
