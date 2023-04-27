package offer.data;

import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.Optional;

@Document(collection = "offers")
public class Offer {
    private String offerId;

    private String hotel_name;
    private String image;
    private String country;
    private String city;

    private Integer stars;

    private LocalDate start_date;
    private LocalDate end_date;
    private String room_type;

    private Integer max_adults;
    private Integer max_children_to_3;
    private Integer max_children_to_10;
    private Integer max_children_to_18;

    private String meals;
    // max price
    private Double price;
    private Boolean available;

    public Offer() {
    }

    public Offer(String offerId, String hotel_name, String image, String country, String city, Integer stars,
                 LocalDate start_date, LocalDate end_date, String room_type, Integer max_adults,
                 Integer max_children_to_3, Integer max_children_to_10, Integer max_children_to_18,
                 String meals, Double price, Boolean available) {
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
    public Optional<String> getHotel_name() {
        return Optional.ofNullable(hotel_name);
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
    public Optional<String> getRoom_type(){
        return Optional.ofNullable(room_type);
    }
    public Optional<String> getImage() {
        return Optional.ofNullable(image);
    }
    public Optional<LocalDate> getStart_date() {
        return Optional.ofNullable(start_date);
    }
    public Optional<LocalDate> getEnd_date() {
        return Optional.ofNullable(end_date);
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
    public Optional<Boolean> getAvailable() { return Optional.ofNullable(available);}

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


}
