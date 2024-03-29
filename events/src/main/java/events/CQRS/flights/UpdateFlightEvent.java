package events.CQRS.flights;

import java.util.HashMap;
import java.util.Optional;

public class UpdateFlightEvent{
    private final String id;
    private final String departure_country;
    private final String departure_city;
    private final String date;
    private final String arrival_country;
    private final String arrival_city;
    private final Integer available_seats;

    private String TO_generated;

    public UpdateFlightEvent(String id, String departure_country, String departure_city, String date,
                             String arrival_country, String arrival_city, Integer available_seats){
        this.id = id;
        this.departure_country = departure_country;
        this.departure_city = departure_city;
        this.date = date;
        this.arrival_country = arrival_country;
        this.arrival_city = arrival_city;
        this.available_seats = available_seats;
        this.TO_generated = "false";
    }
    public void setTO_generated(String TO_generated) {
        this.TO_generated = TO_generated;
    }
    public HashMap<String, Object> getMap() {
        return new HashMap<>() {{
            if (getDate().orElse(null) != null) {
                put("date", getDate());
            }
            if (getArrival_city().orElse(null) != null) {
                put("arrival_city", getArrival_city());
            }
            if (getArrival_country().orElse(null) != null) {
                put("arrival_country", getArrival_country());
            }
            if (getDeparture_city().orElse(null) != null) {
                put("departure_city", getDeparture_city());
            }
            if (getDeparture_country().orElse(null) != null) {
                put("departure_country", getDeparture_country());
            }
            if (getAvailable_seats().orElse(null) != null) {
                put("available_seats", getAvailable_seats());
            }
        }};
    }

    public String getId() {
        return id;
    }

    public String getTO_generated() {
        return TO_generated;
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
