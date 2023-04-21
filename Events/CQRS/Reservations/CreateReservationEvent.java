package Events.CQRS.Reservations;

public class CreateReservationEvent{
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

    public CreateReservationEvent(String id, String offer_id, String flight_id, String room_type, Integer number_of_people, Integer adult, Integer children_to_3, Integer children_to_10, Integer getChildren_to_18, String meals, Integer number_of_days, String start_date, String end_date, Double price, Boolean discount, Boolean is_paid) {
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

    public String getOffer_id() {
        return offer_id;
    }

    public String getFlight_id() {
        return flight_id;
    }

    public Integer getNumber_of_people() {
        return number_of_people;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getIs_paid() {
        return is_paid;
    }

    public String getRoom_type() {
        return room_type;
    }

    public Integer getAdult() {
        return adult;
    }

    public Integer getChildren_to_3() {
        return children_to_3;
    }

    public Integer getChildren_to_10() {
        return children_to_10;
    }

    public Integer getGetChildren_to_18() {
        return getChildren_to_18;
    }

    public String getMeals() {
        return meals;
    }

    public Integer getNumber_of_days() {
        return number_of_days;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public boolean isIs_paid() {
        return is_paid;
    }
}
