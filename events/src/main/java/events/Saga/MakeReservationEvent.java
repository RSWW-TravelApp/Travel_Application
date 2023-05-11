package events.Saga;

import java.io.Serializable;

public class MakeReservationEvent extends SagaEvent {

//    private final String room_type;
//    private final Integer adult;
//    private final Integer children_to_3;
//    private final Integer children_to_10;
//    private final Integer getChildren_to_18;
//    private final String meals;
//    private final Integer number_of_days;
//    private final String start_date;
//    private final String end_date;
//    private final String discount;
    private final String is_paid;


    public MakeReservationEvent(Double price, String offerId, String flightId, Integer seatsNeeded, String user_id,  String payment_id, String reservation_id, String is_paid) {
        super(price, user_id, offerId, flightId, payment_id, reservation_id, seatsNeeded);
        this.is_paid = is_paid;
    }

//    public String getRoom_type() {
//        return room_type;
//    }
//
//    public Integer getAdult() {
//        return adult;
//    }
//
//    public Integer getChildren_to_3() {
//        return children_to_3;
//    }
//
//    public Integer getChildren_to_10() {
//        return children_to_10;
//    }
//
//    public Integer getGetChildren_to_18() {
//        return getChildren_to_18;
//    }
//
//    public String getMeals() {
//        return meals;
//    }
//
//    public Integer getNumber_of_days() {
//        return number_of_days;
//    }
//
//    public String getStart_date() {
//        return start_date;
//    }
//
//    public String getEnd_date() {
//        return end_date;
//    }
//
//    public String getDiscount() {
//        return discount;
//    }

    public String isIs_paid() {
        return is_paid;
    }

    public String getIs_paid() {
        return is_paid;
    }

}
