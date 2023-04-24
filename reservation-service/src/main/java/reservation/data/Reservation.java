package reservation.data;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Optional;

@Document(collection = "reservations")
public class Reservation {
    private String reservationID;

    private String userID;
    private String hotelID;
    private String flightID;

    private Boolean isPaid;

    public Reservation() {
    }

    public Reservation(String reservationID, String userID, String hotelID, String flightID, Boolean isPaid) {
        this.reservationID = reservationID;
        this.userID = userID;
        this.hotelID = hotelID;
        this.flightID = flightID;
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationID='" + reservationID + '\'' +
                ", userID='" + userID + '\'' +
                ", hotelID=" + hotelID +
                ", flightID=" + flightID +
                ", isPaid='" + isPaid + '\'' +
                '}';
    }

    public String getReservationID() {
        return reservationID;
    }
    public Optional<String> getUserID() {
        return Optional.ofNullable(userID);
    }
    public Optional<String> getHotelID() {
        return Optional.ofNullable(hotelID);
    }
    public Optional<String> getFlightID() {
        return Optional.ofNullable(flightID);
    }
    public Optional<Boolean> getIsPaid() {
        return Optional.ofNullable(isPaid);
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }
    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
