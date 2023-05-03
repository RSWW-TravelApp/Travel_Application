package reservationmaster.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@EnableReactiveMongoRepositories
public interface ReservationMasterRepository extends ReactiveMongoRepository<Reservation, String> {
    public Mono<Reservation> findByReservationId(String reservationID);
}
