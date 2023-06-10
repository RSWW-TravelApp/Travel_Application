package events.CQRS.offers;

import java.util.HashMap;
import java.util.Optional;

public class UpdateOfferEvent{
    private final String id;
    private final String hotel_name;
    private final String image;
    private final String country;
    private final String city;
    private final Integer stars;
    private final String start_date;
    private final String end_date;
    private final String room_type;
    private final Integer max_adults;
    private final Integer max_children_to_3;
    private final Integer max_children_to_10;
    private final Integer max_children_to_18;
    private final String meals;
    private final Double price;
    private final String available;

    private String TO_generated;

    public UpdateOfferEvent(String id, String hotel_name, String image, String country, String city, Integer stars, String start_date, String end_date, String room_type, Integer max_adults, Integer max_children_to_3, Integer max_children_to_10, Integer max_children_to_18, String meals, Double price, String available){
        this.id = id;
        this.hotel_name = hotel_name;
        this.image = image;
        this.country = country;
        this.city = city;
        this.stars = stars;
        this.start_date = start_date;
        this.end_date = end_date;
        this.room_type = room_type;
        this.max_adults = max_adults;
        this.max_children_to_3 = max_children_to_3;
        this.max_children_to_10 = max_children_to_10;
        this.max_children_to_18 = max_children_to_18;
        this.meals = meals;
        this.price = price;
        this.available = available;
        this.TO_generated = "false";
    }

    public void setTO_generated(String TO_generated) {
        this.TO_generated = TO_generated;
    }

    public HashMap<String, Object> getMap() {
        return new HashMap<>() {{
            if (getHotel_name().orElse(null) != null) {
                put("hotel_name", getHotel_name());
            }
            if (getCity().orElse(null) != null) {
                put("city", getCity());
            }
            if (getCountry().orElse(null) != null) {
                put("country", getCountry());
            }
            if (getAvailable().orElse(null) != null) {
                put("available", getAvailable());
            }
            if (getEnd_date().orElse(null) != null) {
                put("end_date", getEnd_date());
            }
            if (getStart_date().orElse(null) != null) {
                put("start_date", getStart_date());
            }
            if (getImage().orElse(null) != null) {
                put("image", getImage());
            }
            if (getMax_adults().orElse(null) != null) {
                put("max_adults", getMax_adults());
            }
            if (getMax_children_to_3().orElse(null) != null) {
                put("max_children_to_3", getMax_children_to_3());
            }
            if (getMax_children_to_10().orElse(null) != null) {
                put("max_children_to_10", getMax_children_to_10());
            }
            if (getMax_children_to_18().orElse(null) != null) {
                put("max_children_to_18", getMax_children_to_18());
            }
            if (getMeals().orElse(null) != null) {
                put("meals", getMeals());
            }
            if (getPrice().orElse(null) != null) {
                put("price", getPrice());
            }
            if (getRoom_type().orElse(null) != null) {
                put("room_type", getRoom_type());
            }
            if (getStars().orElse(null) != null) {
                put("stars", getStars());
            }
        }};
    }

    public String getId() {
        return id;
    }

    public String getTO_generated() {
        return TO_generated;
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

    public Optional<String> getCity() {
        return Optional.ofNullable(city);
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

    public Optional<String> getAvailable() {
        return Optional.ofNullable(available);
    }
}
