package Events.Flights;

import java.util.Optional;

public record UpdateFlightEvent(String id, String airline_name, String departure_country,
                                String departure_city, String date,
                                String arrival_country, String arrival_city,
                                Integer available_seats) {

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
