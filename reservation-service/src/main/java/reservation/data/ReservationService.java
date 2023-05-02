package reservation.data;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ReservationService {

    private ReactiveMongoTemplate reactiveMongoTemplate;
    private ReservationRepository reservationRepository;

    public ReservationService(ReactiveMongoTemplate reactiveMongoTemplate, ReservationRepository reservationRepository) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.reservationRepository = reservationRepository;
    }

    public Mono<Reservation> createReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    public Flux<Reservation> getAllReservations(){
        return reservationRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Reservation> findByReservationId(String reservationId){
        return reservationRepository.findByReservationId(reservationId).switchIfEmpty(Mono.empty());
    }

    public Mono<Reservation> deleteReservationById(String reservationId){
        return reservationRepository.findByReservationId(reservationId)
                .flatMap(existingReservation -> reservationRepository.delete(existingReservation)
                        .then(Mono.just(existingReservation)));
    }

    // updating the specific offer with the given parameters (null parameters - don't update the field)
    public Mono<Reservation> updateReservation(String reservationId, String userId, String flightId, String offerId,
                                               boolean isPaid){

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
