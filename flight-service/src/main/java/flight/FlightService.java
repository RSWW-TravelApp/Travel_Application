package flight;

import flight.data.Flight;
import flight.data.FlightRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@Service
public class FlightService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final FlightRepository flightRepository;

    public FlightService(ReactiveMongoTemplate reactiveMongoTemplate, FlightRepository flightRepository) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.flightRepository = flightRepository;
    }

    public Mono<Flight> createFlight(Flight flight){
        return flightRepository.save(flight);
    }

    public Flux<Flight> getAllFlights(){
        return flightRepository.findAll();
    }

    public Mono<Flight> findById(String flightId){
        return flightRepository.findById(flightId);
    }

    public Mono<Flight> deleteFlight(String flightId){
        return flightRepository.findById(flightId)
                .flatMap(existingFlight -> flightRepository.delete(existingFlight)
                        .then(Mono.just(existingFlight)));
    }

    // updating the specific offer with the given parameters (null parameters - don't update the field)
    public Mono<Flight> updateFlight(Flight flight){
        return flightRepository.findById(flight.getFlightId())
                .flatMap(dbFlights -> {
                    flight.getAirline_name().ifPresent(dbFlights::setAirline_name);

                    flight.getArrival_country().ifPresent(dbFlights::setArrival_country);

                    flight.getArrival_city().ifPresent(dbFlights::setArrival_city);

                    flight.getDeparture_country().ifPresent(dbFlights::setDeparture_country);

                    flight.getDeparture_city().ifPresent(dbFlights::setDeparture_city);

                    flight.getAvailable_seats().ifPresent(dbFlights::setAvailable_seats);

                    flight.getDate().ifPresent(dbFlights::setDate);

                    return flightRepository.save(dbFlights);
                });
    }

    // finding all flights that have specific parameters chosen in the Web filter
    public Flux<Flight> fetchFlights(String airline_name, String departure_country, String departure_city,
                                     String arrival_country, String arrival_city, int total_people, LocalDate date){

        Query query = new Query();

        // available_seats has to be >= total number of people of the trip
        // total_people are taken from the Web filter and by default are 0
        query.addCriteria(Criteria.where("available_seats").gte(total_people));


        if(departure_country != null){
            query.addCriteria(Criteria.where("departure_country").regex(departure_country));
        }
        if(arrival_country != null){
            query.addCriteria(Criteria.where("arrival_country").regex(arrival_country));
        }
        if(date != null){
            query.addCriteria(Criteria.where("date").is(date)); // TODO check if "is()" works with the LocalDate type
        }

        return reactiveMongoTemplate
                .find(query, Flight.class);
    }


}
