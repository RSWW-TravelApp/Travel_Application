package flight.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@EnableReactiveMongoRepositories
public interface FlightRepository extends ReactiveMongoRepository<Flight, String> {

    Flux<Flight> findByCountry(String country);
    Mono<Flight> findFlightById(String flightId);
}
