package Events.CQRS.Flights;

import java.util.Optional;

public class UpdateFlightEvent{
    private final String id;
    private final String airline_name;
    private final String departure_country;
    private final String departure_city;
    private final String date;
    private final String arrival_country;
    private final String arrival_city;
    private final Integer available_seats;

    public UpdateFlightEvent(String id, String airline_name, String departure_country, String departure_city, String date, String arrival_country, String arrival_city, Integer available_seats) {
        this.id = id;
        this.airline_name = airline_name;
        this.departure_country = departure_country;
        this.departure_city = departure_city;
        this.date = date;
        this.arrival_country = arrival_country;
        this.arrival_city = arrival_city;
        this.available_seats = available_seats;
    }

    public String getId() {
        return id;
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

    public Optional<String> getDate() {
        return Optional.ofNullable(date);
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
}
