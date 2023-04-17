package Events.Offers;

import java.util.Optional;

public record UpdateOfferEvent(String id, String hotel_name, String image,
                               String country, Integer stars, String start_date,
                               String end_date, String room_type, Integer max_adults,
                               Integer max_children_to_3, Integer max_children_to_10,
                               Integer max_children_to_18, String meals, Double price,
                               Boolean available) {

    public String getId() {
        return id;
    }

    public Optional<String> getHotel_name() {
        return Optional.ofNullable(hotel_name);
    }

    public Optional<String> getImage() {
        return Optional.ofNullable(image);
    }

    public Optional<String> getCountry() {
        return Optional.ofNullable(country);
    }

    public Optional<Integer> getStars() {
        return Optional.ofNullable(stars);
    }

    public Optional<String> getStart_date() {
        return Optional.ofNullable(start_date);
    }

    public Optional<String> getEnd_date() {
        return Optional.ofNullable(end_date);
    }

    public Optional<String> getRoom_type() {
        return Optional.ofNullable(room_type);
    }

    public Optional<Integer> getMax_adults() {
        return Optional.ofNullable(max_adults);
    }

    public Optional<Integer> getMax_children_to_3() {
        return Optional.ofNullable(max_children_to_3);
    }

    public Optional<Integer> getMax_children_to_10() {
        return Optional.ofNullable(max_children_to_10);
    }

    public Optional<Integer> getMax_children_to_18() {
        return Optional.ofNullable(max_children_to_18);
    }

    public Optional<String> getMeals() {
        return Optional.ofNullable(meals);
    }

    public Optional<Double> getPrice() {
        return Optional.ofNullable(price);
    }

    public Optional<Boolean> getAvailable() {
        return Optional.ofNullable(available);
    }
}
