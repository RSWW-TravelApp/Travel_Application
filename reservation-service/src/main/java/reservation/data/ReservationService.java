package reservation.data;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;


@Service
public class ReservationService {

    private ReactiveMongoTemplate reactiveMongoTemplate;
    private ReservationRepository reservationRepository;

    public ReservationService(ReactiveMongoTemplate reactiveMongoTemplate, ReservationRepository reservationRepository) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.reservationRepository = reservationRepository;
    }

    public Mono<Reservation> createReservation(Reservation reservation){
        return  reservationRepository.findByReservationId(reservation.getReservationId()).switchIfEmpty(
                reservationRepository.save(reservation));
    }

    public Flux<Reservation> getAllReservations(){
        return reservationRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Flux<Reservation> getAllByUserId(String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return reactiveMongoTemplate.find(query, Reservation.class).switchIfEmpty(Mono.empty());
    }

    public Mono<Reservation> findByReservationId(String reservationId){
        return reservationRepository.findByReservationId(reservationId).switchIfEmpty(Mono.empty());
    }

    public Mono<Reservation> deleteReservationById(String reservationId){
        return reservationRepository.findByReservationId(reservationId)
                .flatMap(existingReservation -> reservationRepository.delete(existingReservation)
                        .then(Mono.just(existingReservation)));
    }

    public Flux<Reservation> fetchReservations(String userId, String flightId, String offerId, Boolean isPaid,
                                               Boolean isCancelled, Boolean isReserved){

        Query query = new Query();

        if(userId != null){
            query.addCriteria(Criteria.where("userId").regex(userId));
        }
        if(flightId != null){
            query.addCriteria(Criteria.where("flightId").regex(flightId));
        }
        if(offerId != null){
            query.addCriteria(Criteria.where("offerId").regex(offerId));
        }
        if(isPaid != null){
            query.addCriteria(Criteria.where("isPaid").is(isPaid));
        }
        if(isCancelled != null){
            query.addCriteria(Criteria.where("isCancelled").is(isCancelled));
        }
        if(isCancelled != null){
            query.addCriteria(Criteria.where("isReserved").is(isReserved));
        }

        return reactiveMongoTemplate.find(query, Reservation.class);
    }

    // updating the specific offer with the given parameters (null parameters - don't update the field)
    public Mono<Reservation> updateReservation(String reservationId, String userId, String offerId, String flightId,
                                               Boolean isPaid, Boolean isCancelled, Double price, Integer travellers, String paymentId, Boolean isReserved){

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
        if(isCancelled != null) {
            update.set("isCancelled", isCancelled);
        }
        if(price != null) {
            update.set("price", price);
        }
        if(travellers != null) {
            update.set("Travellers", travellers);
        }
        if(paymentId != null) {
            update.set("paymentId", paymentId);
        }
        if(isReserved != null) {
            update.set("isReserved", isReserved);
        }
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        return reactiveMongoTemplate.findAndModify(query, update, options, Reservation.class);
    }


}
