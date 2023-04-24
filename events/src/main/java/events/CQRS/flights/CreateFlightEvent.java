package events.CQRS.flights;

public class CreateFlightEvent {
    private final String id;
    private final String airline_name;
    private final String departure_country;
    private final String departure_city;
    private final String date;
    private final String arrival_country;
    private final String arrival_city;
    private final Integer available_seats;

    public CreateFlightEvent(String id, String airline_name, String departure_country, String departure_city, String date, String arrival_country, String arrival_city, Integer available_seats) {
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

    public String getAirline_name() {
        return airline_name;
    }

    public String getDeparture_country() {
        return departure_country;
    }

    public String getDeparture_city() {
        return departure_city;
    }

    public String getDate() {
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
