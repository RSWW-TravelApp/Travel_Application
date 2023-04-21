package Events.CQRS.Reservations;

import java.util.Optional;

public class UpdateReservationEvent{
    private final String id;
    private final String offer_id;
    private final String flight_id;
    private final String room_type;
    private final Integer number_of_people;
    private final Integer adult;
    private final Integer children_to_3;
    private final Integer children_to_10;
    private final Integer getChildren_to_18;
    private final String meals;
    private final Integer number_of_days;
    private final String start_date;
    private final String end_date;
    private final Double price;
    private final Boolean discount;
    private final Boolean is_paid;

    public UpdateReservationEvent(String id, String offer_id, String flight_id, String room_type, Integer number_of_people, Integer adult, Integer children_to_3, Integer children_to_10, Integer getChildren_to_18, String meals, Integer number_of_days, String start_date, String end_date, Double price, Boolean discount, Boolean is_paid) {
        this.id = id;
        this.offer_id = offer_id;
        this.flight_id = flight_id;
        this.room_type = room_type;
        this.number_of_people = number_of_people;
        this.adult = adult;
        this.children_to_3 = children_to_3;
        this.children_to_10 = children_to_10;
        this.getChildren_to_18 = getChildren_to_18;
        this.meals = meals;
        this.number_of_days = number_of_days;
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.discount = discount;
        this.is_paid = is_paid;
    }

    public String getId() {
        return id;
    }

    public Optional<String> getOffer_id() {
        return Optional.ofNullable(offer_id);
    }

    public Optional<String> getFlight_id() {
        return Optional.ofNullable(flight_id);
    }

    public Optional<String> getRoom_type() {
        return Optional.ofNullable(room_type);
    }

    public Optional<Integer> getNumber_of_people() {
        return Optional.ofNullable(number_of_people);
    }

    public Optional<Integer> getAdult() {
        return Optional.ofNullable(adult);
    }

    public Optional<Integer> getChildren_to_3() {
        return Optional.ofNullable(children_to_3);
    }

    public Optional<Integer> getChildren_to_10() {
        return Optional.ofNullable(children_to_10);
    }

    public Optional<Integer> getGetChildren_to_18() {
        return Optional.ofNullable(getChildren_to_18);
    }

    public Optional<String> getMeals() {
        return Optional.ofNullable(meals);
    }

    public Optional<Integer> getNumber_of_days() {
        return Optional.ofNullable(number_of_days);
    }

    public Optional<String> getStart_date() {
        return Optional.ofNullable(start_date);
    }

    public Optional<String> getEnd_date() {
        return Optional.ofNullable(end_date);
    }

    public Optional<Double> getPrice() {
        return Optional.ofNullable(price);
    }

    public Optional<Boolean> getDiscount() {
        return Optional.ofNullable(discount);
    }

    public Optional<Boolean> getIs_paid() {
        return Optional.ofNullable(is_paid);
    }
}
