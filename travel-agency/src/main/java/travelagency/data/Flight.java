package travelagency.data;

import events.CQRS.flights.UpdateFlightEvent;
import events.CQRS.offers.UpdateOfferEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.*;

@Document(collection = "flights")
public class Flight {
    @Id
    private String flightId;
    private String departure_country;
    private String departure_city;
    private String arrival_country;
    private String arrival_city;

    private Integer available_seats;

    private LocalDate date;
    private List<FlightNested> events;

    public Flight() {

    }

    public Flight(String flightId, String departure_country, String departure_city, String arrival_country,
                  String arrival_city, Integer available_seats, LocalDate date, List<FlightNested> events) {
        this.flightId = flightId;
        this.departure_country = departure_country;
        this.departure_city = departure_city;
        this.arrival_country = arrival_country;
        this.arrival_city = arrival_city;
        this.available_seats = available_seats;
        this.date = date;
        this.events = events;
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

    public String getDeparture_country() {
        return departure_country;
    }

    public String getDeparture_city() {
        return departure_city;
    }

    public String getArrival_country() {
        return arrival_country;
    }

    public String getArrival_city() {
        return arrival_city;
    }

    public Integer getAvailable_seats() {
        return available_seats;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<FlightNested> getEvents() {
        return events;
    }

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
    public void setEvents(List<FlightNested> events){this.events = events;}

    public HashMap<String, Map.Entry<Object, Object>> getChanges(UpdateFlightEvent stateAfterEvent) {
        return new HashMap<>() {{
            if (stateAfterEvent.getArrival_city().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getArrival_city().orElse(null), getArrival_city())) {
                put("arrival_city", new SimpleEntry<>(getArrival_city(), stateAfterEvent.getArrival_city().orElse(null)));
            }
            if (stateAfterEvent.getDate().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getDate().map(LocalDate::parse).orElse(null), getDate())) {
                put("date", new SimpleEntry<>(getDate(), stateAfterEvent.getDate().orElse(null)));
            }
            if (stateAfterEvent.getAvailable_seats().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getAvailable_seats().orElse(null), getAvailable_seats())) {
                put("available_seats", new SimpleEntry<>(getAvailable_seats(), stateAfterEvent.getAvailable_seats().orElse(null)));
            }
            if (stateAfterEvent.getArrival_country().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getArrival_country().orElse(null), getArrival_country())) {
                put("arrival_country", new SimpleEntry<>(getArrival_country(), stateAfterEvent.getArrival_country().orElse(null)));
            }
            if (stateAfterEvent.getDeparture_city().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getDeparture_city().orElse(null), getDeparture_city())) {
                put("departure_city", new SimpleEntry<>(getDeparture_city(), stateAfterEvent.getDeparture_city().orElse(null)));
            }
            if (stateAfterEvent.getDeparture_country().orElse(null) != null &&
                    !Objects.equals(stateAfterEvent.getDeparture_country().orElse(null), getDeparture_country())) {
                put("departure_country", new SimpleEntry<>(getDeparture_country(), stateAfterEvent.getDeparture_country().orElse(null)));
            }
        }};
    }
}
