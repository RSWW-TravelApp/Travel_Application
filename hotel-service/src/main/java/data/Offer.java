package data;


import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Optional;


@ToString
//@AllArgsConstructor
//@NoArgsConstructor
@Data
@Document(collection = "offers")
public class Offer {

    @Field("_id")
   //@JsonProperty("id")
    @Id
    private String offerId;
    private String hotel_name;
    private String image;
    private String country;
    private String city;
    private int stars;

    private LocalDate start_date;
    private LocalDate end_date;
    private String room_type;

    private int max_adults;
    private int max_children_to_3;
    private int max_children_to_10;
    private int max_children_to_18;

    private String meals;
    // max price
    private double price;


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



    public void setImage(String image) {
        this.image = image;
    }
    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }
    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }
    public void setMax_adults(int max_adults) {
        this.max_adults = max_adults;
    }
    public void setMax_children_to_3(int max_children_to_3) {
        this.max_children_to_3 = max_children_to_3;
    }
    public void setMax_children_to_10(int max_children_to_10) {
        this.max_children_to_10 = max_children_to_10;
    }
    public void setMax_children_to_18(int max_children_to_18) {
        this.max_children_to_18 = max_children_to_18;
    }
    public void setMeals(String meals) {
        this.meals = meals;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }
}
