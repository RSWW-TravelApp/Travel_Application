package Events.Flights;

import java.time.LocalDate;

public class CreateFlight{
    private final String airline_name;
    private final String departure_country;
    private final String departure_city;
    private final LocalDate date;
    private final String arrival_country;
    private final String arrival_city;
    private final Integer available_seats;

    public CreateFlight(String airline_name, String departure_country, String departure_city, LocalDate date, String arrival_country, String arrival_city, Integer available_seats) {
        this.airline_name = airline_name;
        this.departure_country = departure_country;
        this.departure_city = departure_city;
        this.date = date;
        this.arrival_country = arrival_country;
        this.arrival_city = arrival_city;
        this.available_seats = available_seats;
    }

    public String getAirline_name() {
        return airline_name;
    }

    public String getDeparture_country() {
        return departure_country;
    }

    public String getDeparture_city() {
        return departure_city;
    }

    public LocalDate getDate() {
        return date;
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
}
