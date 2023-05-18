package travelagency.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.Optional;

@Document(collection = "flightsNested")
public class FlightNested {

    @Id
    private String flightId;
    private String departure_country;
    private String departure_city;
    private String arrival_country;
    private String arrival_city;
    private Integer available_seats;
    private LocalDate date;
    private String eventType;

    public FlightNested() {
    }

    public FlightNested(String flightId, String departure_country, String departure_city, String arrival_country,
                        String arrival_city, Integer available_seats, LocalDate date, String eventType) {
        this.flightId = flightId;
        this.departure_country = departure_country;
        this.departure_city = departure_city;
        this.arrival_country = arrival_country;
        this.arrival_city = arrival_city;
        this.available_seats = available_seats;
        this.date = date;
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id='" + flightId + '\'' +
                ", departure_country=" + departure_country +
                ", departure_city=" + departure_city +
                ", arrival_country='" + arrival_country + '\'' +
                ", arrival_city=" + arrival_city +
                ", available_seats=" + available_seats +
                ", date=" + date +
                '}';
    }

    public String getFlightId() {
        return flightId;
    }
    public Optional<String> getDeparture_country() {
        return Optional.ofNullable(departure_country);
    }
    public Optional<String> getDeparture_city() {
        return Optional.ofNullable(departure_city);
    }
    public Optional<String> getArrival_country() {
        return Optional.ofNullable(arrival_country);
    }
    public Optional<String> getArrival_city() {
        return Optional.ofNullable(arrival_city);
    }
    public Optional<Integer> getAvailable_seats() {
        return Optional.ofNullable(available_seats);
    }
    public Optional<LocalDate> getDate() {
        return Optional.ofNullable(date);
    }
    public Optional<String> getEventType() {return Optional.ofNullable(eventType);}

    public void setFlightId(String flightId) { this.flightId = flightId; }
    public void setDeparture_country(String departure_country) {
        this.departure_country = departure_country;
    }
    public void setDeparture_city(String departure_city) {
        this.departure_city = departure_city;
    }
    public void setArrival_country(String arrival_country) {
        this.arrival_country = arrival_country;
    }
    public void setArrival_city(String arrival_city) {
        this.arrival_city = arrival_city;
    }
    public void setAvailable_seats(Integer available_seats) {
        this.available_seats = available_seats;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setEventType(String eventType) {this.eventType = eventType;}

}
