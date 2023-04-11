package Events.Offers;

import java.time.LocalDate;
import java.util.Optional;

public class CreateOffer {
    private final String hotel_name;
    private final String image;
    private final String country;
    private final Integer stars;
    private final LocalDate start_date;
    private final LocalDate end_date;
    private final String room_type;
    private final Integer max_adults;
    private final Integer max_children_to_3;
    private final Integer max_children_to_10;
    private final Integer max_children_to_18;
    private final String meals;
    private final Double price;

    public CreateOffer(String hotel_name, String image, String country, Integer stars, LocalDate start_date, LocalDate end_date, String room_type, Integer max_adults, Integer max_children_to_3, Integer max_children_to_10, Integer max_children_to_18, String meals, Double price) {
        this.hotel_name = hotel_name;
        this.image = image;
        this.country = country;
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
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public String getImage() {
        return image;
    }

    public String getCountry() {
        return country;
    }

    public Integer getStars() {
        return stars;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public String getRoom_type() {
        return room_type;
    }

    public Integer getMax_adults() {
        return max_adults;
    }

    public Integer getMax_children_to_3() {
        return max_children_to_3;
    }

    public Integer getMax_children_to_10() {
        return max_children_to_10;
    }

    public Integer getMax_children_to_18() {
        return max_children_to_18;
    }

    public String getMeals() {
        return meals;
    }

    public Double getPrice() {
        return price;
    }
}
