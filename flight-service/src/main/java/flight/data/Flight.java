package flight.data;

//import lombok.Data;
//import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Optional;


//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
@Document(collection = "flights")
public class Flight {

    @Field("_id")
    //@JsonProperty("id")
    @Id
    private String flightId;

    private String airline_name;
    private String departure_country;
    private String departure_city;
    private String arrival_country;
    private String arrival_city;

    private Integer available_seats;

    private LocalDate date;

    public Flight(String flightId, String airline_name, String departure_country, String departure_city, String arrival_country, String arrival_city, int available_seats, LocalDate date) {
        this.flightId = flightId;
        this.airline_name = airline_name;
        this.departure_country = departure_country;
        this.departure_city = departure_city;
        this.arrival_country = arrival_country;
        this.arrival_city = arrival_city;
        this.available_seats = available_seats;
        this.date = date;
    }

    public String getFlightId() {
        return flightId;
    }
    public Optional<String> getAirline_name() {
        return Optional.ofNullable(airline_name);
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
        return Optional.of(available_seats);
    }
    public Optional<LocalDate> getDate() {
        return Optional.ofNullable(date);
    }


    public void setAirline_name(String airline_name) {
        this.airline_name = airline_name;
    }
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
    public void setAvailable_seats(int available_seats) {
        this.available_seats = available_seats;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
