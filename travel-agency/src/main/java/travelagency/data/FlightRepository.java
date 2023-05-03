package travelagency.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@EnableReactiveMongoRepositories
public interface FlightRepository extends ReactiveMongoRepository<Flight, String> {

    Mono<Flight> findByFlightId(String flightId);
}
