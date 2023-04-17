package Events.Flights;

public record CreateFlightEvent(String id, String airline_name, String departure_country,
                                String departure_city, String date,
                                String arrival_country, String arrival_city,
                                Integer available_seats) {

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
