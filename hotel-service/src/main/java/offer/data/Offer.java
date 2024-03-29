package offer.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Optional;

@Document(collection = "offers")
public class Offer {
    @Id
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

    public Boolean getAvailable() {
        return available;
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


}
