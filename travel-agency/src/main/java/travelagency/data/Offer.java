package travelagency.data;

import events.CQRS.offers.UpdateOfferEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.util.function.Tuple2;

import java.time.LocalDate;
import java.util.*;

@Document(collection = "offers")
public class Offer {
    @Id
    private String offerId;

    private String hotel_name;
    private String image;
    private String country;
    private String city;
    private String meals;

    private LocalDate start_date;
    private LocalDate end_date;
    private String room_type;

    private Integer stars;
    private Integer max_adults;
    private Integer max_children_to_3;
    private Integer max_children_to_10;
    private Integer max_children_to_18;

    private Double price;
    private Boolean available;
    private List<OfferNested> events;


    public Offer() {
    }

    public Offer(String offerId, String hotel_name, String image, String country, String city, Integer stars,
                 LocalDate start_date, LocalDate end_date, String room_type, Integer max_adults,
                 Integer max_children_to_3, Integer max_children_to_10, Integer max_children_to_18,
                 String meals, Double price, Boolean available, List<OfferNested> events) {
        this.offerId = offerId;
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
        this.events = events;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id='" + offerId + '\'' +
                ", hotel_name='" + hotel_name + '\'' +
                ", stars=" + stars +
                ", price=" + price +
                ", room_type='" + room_type + '\'' +
                ", adults=" + max_adults +
                ", children_to_3=" + max_children_to_3 +
                ", children_to_10=" + max_children_to_10 +
                ", children_to_18=" + max_children_to_18 +
                ", meals='" + meals + '\'' +
                ", country='" + country + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", available=" + available +
                '}';
    }

    public String getOfferId() {
        return offerId;
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

    public String getCity() {
        return city;
    }

    public String getMeals() {
        return meals;
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

    public Integer getStars() {
        return stars;
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

    public Double getPrice() {
        return price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public List<OfferNested> getEvents() {
        return events;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }
    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }
    public void setHotel_name(String hotel_name){this.hotel_name = hotel_name;}
    public void setCountry(String country) {
        this.country = country;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setStars(Integer stars) {
        this.stars = stars;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }
    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }
    public void setMax_adults(Integer max_adults) {
        this.max_adults = max_adults;
    }
    public void setMax_children_to_3(Integer max_children_to_3) {
        this.max_children_to_3 = max_children_to_3;
    }
    public void setMax_children_to_10(Integer max_children_to_10) {
        this.max_children_to_10 = max_children_to_10;
    }
    public void setMax_children_to_18(Integer max_children_to_18) {
        this.max_children_to_18 = max_children_to_18;
    }
    public void setMeals(String meals) {
        this.meals = meals;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setAvailable(Boolean available) { this.available = available; }
    public void setEvents(List<OfferNested> events){this.events = events;}

    public HashMap<String, Map.Entry<Object, Object>> getChanges(UpdateOfferEvent stateAfterEvent) {
        return new HashMap<>() {{
            if (stateAfterEvent.getHotel_name().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getHotel_name().orElse(null), getHotel_name())) {
                put("hotel_name", new SimpleEntry<>(getHotel_name(), stateAfterEvent.getHotel_name().orElse(null)));
            }
            if (stateAfterEvent.getCity().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getCity().orElse(null), getCity())) {
                put("city", new SimpleEntry<>(getCity(), stateAfterEvent.getCity().orElse(null)));
            }
            if (stateAfterEvent.getCountry().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getCountry().orElse(null), getCountry())) {
                put("country", new SimpleEntry<>(getCountry(), stateAfterEvent.getCountry().orElse(null)));
            }
            if (stateAfterEvent.getAvailable().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getAvailable().map(Boolean::parseBoolean).orElse(null), getAvailable())) {
                put("available", new SimpleEntry<>(getAvailable(), stateAfterEvent.getAvailable().orElse(null)));
            }
            if (stateAfterEvent.getEnd_date().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getEnd_date().map(LocalDate::parse).orElse(null), getEnd_date())) {
                put("end_date", new SimpleEntry<>(getEnd_date(), stateAfterEvent.getEnd_date().orElse(null)));
            }
            if (stateAfterEvent.getStars().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getStars().orElse(null), getStars())) {
                put("stars", new SimpleEntry<>(getStars(), stateAfterEvent.getStars().orElse(null)));
            }
            if (stateAfterEvent.getStart_date().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getStart_date().map(LocalDate::parse).orElse(null), getStart_date())) {
                put("start_date", new SimpleEntry<>(getStart_date(), stateAfterEvent.getStart_date().orElse(null)));
            }
            if (stateAfterEvent.getImage().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getImage().orElse(null), getImage())) {
                put("image", new SimpleEntry<>(getImage(), stateAfterEvent.getImage().orElse(null)));
            }
            if (stateAfterEvent.getMax_adults().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getMax_adults().orElse(null), getMax_adults())) {
                put("max_adults", new SimpleEntry<>(getMax_adults(), stateAfterEvent.getMax_adults().orElse(null)));
            }
            if (stateAfterEvent.getMax_children_to_3().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getMax_children_to_3().orElse(null), getMax_children_to_3())) {
                put("max_children_to_3", new SimpleEntry<>(getMax_children_to_3(), stateAfterEvent.getMax_children_to_3().orElse(null)));
            }
            if (stateAfterEvent.getMax_children_to_10().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getMax_children_to_10().orElse(null), getMax_children_to_10())) {
                put("max_children_to_10", new SimpleEntry<>(getMax_children_to_10(), stateAfterEvent.getMax_children_to_10().orElse(null)));
            }
            if (stateAfterEvent.getMax_children_to_18().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getMax_children_to_18().orElse(null), getMax_children_to_18())) {
                put("max_children_to_18", new SimpleEntry<>(getMax_children_to_18(), stateAfterEvent.getMax_children_to_18().orElse(null)));
            }
            if (stateAfterEvent.getPrice().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getPrice().orElse(null), getPrice())) {
                put("price", new SimpleEntry<>(getPrice(), stateAfterEvent.getPrice().orElse(null)));
            }
            if (stateAfterEvent.getMeals().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getMeals().orElse(null), getMeals())) {
                put("meals", new SimpleEntry<>(getMeals(), stateAfterEvent.getMeals().orElse(null)));
            }
            if (stateAfterEvent.getRoom_type().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getRoom_type().orElse(null), getRoom_type())) {
                put("room_type", new SimpleEntry<>(getRoom_type(), stateAfterEvent.getRoom_type().orElse(null)));
            }
        }};
    }
}
