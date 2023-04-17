package Events.Offers;

public record CreateOfferEvent(String hotel_name, String image, String country,
                               Integer stars, String start_date, String end_date,
                               String room_type, Integer max_adults,
                               Integer max_children_to_3, Integer max_children_to_10,
                               Integer max_children_to_18, String meals, Double price,
                               Boolean available) {

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

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
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
}
