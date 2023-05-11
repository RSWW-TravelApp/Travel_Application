package reservation.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@EnableReactiveMongoRepositories
public interface ReservationRepository extends ReactiveMongoRepository<Reservation, String> {

    Mono<Reservation> findByReservationId(String reservationId);
}
