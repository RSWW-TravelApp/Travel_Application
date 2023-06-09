package flight.data;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class FlightService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final FlightRepository flightRepository;
    private List<Pair<String,Integer>> currentDestinationsTop10 = new ArrayList<>();
    public FlightService(ReactiveMongoTemplate reactiveMongoTemplate, FlightRepository flightRepository) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.flightRepository = flightRepository;
    }
    public List<Pair<String,Integer>> getCurrentDestinationsTop10() {
        return currentDestinationsTop10;
    }

    public String getCurrentDestinationsTop10String() {
        return getString(currentDestinationsTop10);
    }

    public void setCurrentDestinationsTop10(List<Pair<String, Integer>> currentDestinationsTop10) {
        this.currentDestinationsTop10 = currentDestinationsTop10;
    }

    @NotNull
    private String getString(List<Pair<String,Integer>> list) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
        {
            temp.append(i + 1).append(". ").append(list.get(i).getFirst()).append("\n");
        }
        return temp.toString();
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
                                     String arrival_city, Integer available_seats, LocalDate date){

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
        if (available_seats != null) {
            update.set("available_seats", available_seats);
        }
        if (date != null) {
            update.set("date", date);
        }

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Flight.class);
    }


    // finding all flights that have specific parameters chosen in the Web filter
    public Flux<Flight> fetchFlights(String departure_country, String departure_city, String arrival_country,
                                     String arrival_city, Integer available_seats, LocalDate date){
        Query query = new Query();

        if(departure_country != null){
            query.addCriteria(Criteria.where("departure_country").regex(departure_country));
        }
        if(departure_city != null){
            query.addCriteria(Criteria.where("departure_city").regex(departure_city));
        }
        if(arrival_country != null){
            query.addCriteria(Criteria.where("arrival_country").regex(arrival_country));
        }
        if(arrival_city != null){
            query.addCriteria(Criteria.where("arrival_city").regex(arrival_city));
        }
        if(available_seats != null){
            query.addCriteria(Criteria.where("available_seats").gte(available_seats));
        }
        if(date != null){
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            query.addCriteria(Criteria.where("date").gte(startOfDay).lt(endOfDay));
        }

        return reactiveMongoTemplate
                .find(query, Flight.class);
    }


}
