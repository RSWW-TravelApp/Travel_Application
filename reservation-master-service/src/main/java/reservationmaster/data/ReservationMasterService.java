package reservationmaster.data;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ReservationMasterService {

    private ReactiveMongoTemplate reactiveMongoTemplate;
    private ReservationMasterRepository reservationMasterRepository;

    public ReservationMasterService(ReactiveMongoTemplate reactiveMongoTemplate, ReservationMasterRepository reservationMasterRepository) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.reservationMasterRepository = reservationMasterRepository;
    }

    public Mono<Reservation> createReservation(Reservation reservation){
        return reservationMasterRepository.save(reservation);
    }

    public Flux<Reservation> getAllReservations(){
        return reservationMasterRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Reservation> findByReservationId(String reservationId){
        return reservationMasterRepository.findByReservationId(reservationId).switchIfEmpty(Mono.empty());
    }

    public Mono<Reservation> deleteByReservationId(String reservationId){
        return reservationMasterRepository.findByReservationId(reservationId)
                .flatMap(existingReservation -> reservationMasterRepository.delete(existingReservation)
                        .then(Mono.just(existingReservation)));
    }

    public Mono<Reservation> updateReservation(String reservationId, String userId, String flightId, String offerId,
                                               Boolean isPaid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reservationId").is(reservationId));

        Update update = new Update();
        if(userId != null) {
            update.set("userId", userId);
        }
        if(flightId != null) {
            update.set("flightId", flightId);
        }
        if(offerId != null) {
            update.set("offerId", offerId);
        }
        if(!isPaid) {
            update.set("isPaid", false);
        }

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Reservation.class);

    }
}
