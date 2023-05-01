package flight.data;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
        return flightRepository.findAll()
                .switchIfEmpty(Flux.empty());
    }

    public Mono<Flight> findByFlightId(String flightId){
        return flightRepository.findByFlightId(flightId)
                .switchIfEmpty(Mono.empty());
    }

    public Mono<Flight> deleteByFlightId(String flightId){
        return flightRepository.findByFlightId(flightId)
                .flatMap(existingFlight -> flightRepository.delete(existingFlight)
                        .then(Mono.just(existingFlight)));
    }

    // updating the specific flight with the given parameters (null parameters - don't update the field)
    public Mono<Flight> updateFlight(String flightId, String departure_country, String departure_city, String arrival_country,
                                     String arrival_city, Integer available_seats, LocalDate date, Double price ){

        Query query = new Query();
        query.addCriteria(Criteria.where("flightId").is(flightId));

        Update update = new Update();
        if (departure_country != null) {
            update.set("departure_country", departure_country);
        }
        if (departure_city != null) {
            update.set("departure_city", departure_city);
        }
        if (arrival_country != null) {
            update.set("arrival_country", arrival_country);
        }
        if (arrival_city != null) {
            update.set("arrival_city", arrival_city);
        }
        if (available_seats != 0) {
            update.set("available_seats", available_seats);
        }
        if (date != null) {
            update.set("date", date);
        }
        if (price != null) {
            update.set("price", price);
        }


        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Flight.class);
    }



    // finding all flights that have specific parameters chosen in the Web filter
    // TODO: check
    public Flux<Flight> fetchFlights(String departure_country, String departure_city,
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
