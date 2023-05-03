package reservationmaster.data;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    public Mono<Reservation> deleteReservationById(String reservationId){
        return reservationMasterRepository.findByReservationId(reservationId)
                .flatMap(existingReservation -> reservationMasterRepository.delete(existingReservation)
                        .then(Mono.just(existingReservation)));
    }

    public Mono<ReservationNested> addEvent(ReservationNested reservationNested){
        Query query = new Query();
        query.addCriteria(Criteria.where("reservationId").is(reservationNested.getReservationId()));

        Update update = new Update()
                .push("events", reservationNested);

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        return reactiveMongoTemplate.findAndModify(query, update, options, ReservationNested.class);
    }

    public Flux<Reservation> getAllReservations(){
        return reservationMasterRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Reservation> findByReservationId(String reservationId){
        return reservationMasterRepository.findByReservationId(reservationId).switchIfEmpty(Mono.empty());
    }

    // updating the specific offer with the given parameters (null parameters - don't update the field)
    public Mono<Reservation> updateReservation(String reservationId, String userId, String flightId, String offerId,
                                               Boolean isPaid, List<ReservationNested> events){

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
        if(isPaid != null) {
            update.set("isPaid", isPaid);
        }
        if(events != null){
            update.set("events", events);
        }

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Reservation.class);
    }


}

