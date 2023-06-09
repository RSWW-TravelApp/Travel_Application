package reservationmaster.data;

import events.CQRS.reservations.CreateReservationEvent;
import events.CQRS.reservations.DeleteReservationEvent;
import events.CQRS.reservations.UpdateReservationEvent;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reservationmaster.ReservationMasterEvent;

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
        return reservationMasterRepository.save(reservation).flatMap(savedReservation ->
                addEvent(new ReservationNested(
                        savedReservation.getReservationId(),
                        savedReservation.getUserId(),
                        savedReservation.getFlightId(),
                        savedReservation.getOfferId(),
                        savedReservation.getPaymentId(),
                        savedReservation.getPrice(),
                        savedReservation.getIsPaid(),
                        savedReservation.getIsCancelled(),
                        savedReservation.getTravellers(),
                        savedReservation.getReserved(),
                        "CreateReservation"))
        );

    }

    public Mono<Reservation> deleteReservationById(String reservationId){
        return reservationMasterRepository.findByReservationId(reservationId)
                .flatMap(existingReservation -> reservationMasterRepository.delete(existingReservation)
                        .then(Mono.just(existingReservation)));
    }

    public Mono<Reservation> addEvent(ReservationNested reservationNested){
        Query query = new Query();
        query.addCriteria(Criteria.where("reservationId").is(reservationNested.getReservationId()));
        Update update = new Update()
                .push("events", reservationNested);
        String userId = null,offerId= null,flightId= null,isPaid= null,isCancelled= null, paymendId= null, isReserved= null;
        Double price = null;
        Integer travellers = null;
        System.out.println("Reservation CRUD " + reservationNested.getEventType() + " for ID: " + reservationNested.getReservationId());
        if(reservationNested.getUserId() != null) {
            userId = reservationNested.getUserId();
            update.set("userId", reservationNested.getUserId());
        }
        if(reservationNested.getOfferId() != null) {
            offerId = reservationNested.getOfferId();
            update.set("offerId", reservationNested.getOfferId());
        }
        if(reservationNested.getFlightId() != null) {
            flightId = reservationNested.getFlightId();
            update.set("flightId", reservationNested.getFlightId());
        }
        if(reservationNested.getPaymentId() != null){
            paymendId = reservationNested.getPaymentId();
            update.set("paymentId", reservationNested.getPaymentId());
        }
        if(reservationNested.getPrice() != null){
            price = reservationNested.getPrice();
            update.set("price", reservationNested.getPrice());
        }
        if(reservationNested.getPaid() != null) {
            isPaid = reservationNested.getPaid().toString();
            update.set("isPaid", reservationNested.getPaid());
        }
        if(reservationNested.getCancelled() != null){
            isCancelled = reservationNested.getCancelled().toString();
            update.set("isCancelled", reservationNested.getCancelled());
        }
        if(reservationNested.getTravellers() != null) {
            travellers = reservationNested.getTravellers();
            update.set("travellers", reservationNested.getTravellers());
        }
        if(reservationNested.getReserved() != null){
            isReserved = reservationNested.getReserved().toString();
            update.set("isReserved", reservationNested.getReserved());
        }

        switch (reservationNested.getEventType())
        {
            case "CreateReservation":
                ReservationMasterEvent.sink_CQRS_create.tryEmitNext(new CreateReservationEvent(reservationNested.getReservationId(),userId,offerId,flightId,isPaid,isCancelled,price,travellers,paymendId,isReserved));
                break;
            case "DeleteReservation":
                ReservationMasterEvent.sink_CQRS_delete.tryEmitNext(new DeleteReservationEvent(reservationNested.getReservationId()));
                break;
            case "CompleteReservation":
                break;
            default:
                ReservationMasterEvent.sink_CQRS_update.tryEmitNext(new UpdateReservationEvent(reservationNested.getReservationId(),userId,offerId,flightId,isPaid,isCancelled,price,travellers,paymendId,isReserved));
        }
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Reservation.class).log();
    }

    public Flux<Reservation> getAllReservations(){
        return reservationMasterRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Reservation> findByReservationId(String reservationId){
        return reservationMasterRepository.findByReservationId(reservationId).switchIfEmpty(Mono.empty());
    }

    // updating the specific offer with the given parameters (null parameters - don't update the field)
    public Mono<Reservation> updateReservation(String reservationId, String userId, String flightId, String offerId, String paymentId,
                                               Boolean isPaid, List<ReservationNested> events,Boolean isCancelled, Boolean isReserved, Double price, Integer travellers){

        Query query = new Query();
        query.addCriteria(Criteria.where("reservationId").is(reservationId));

        Update update = new Update();
        if(userId != null) {
            update.set("userId", userId);
        }
        if(offerId != null) {
            update.set("offerId", offerId);
        }
        if(flightId != null) {
            update.set("flightId", flightId);
        }
        if(paymentId != null){
            update.set("paymentId", paymentId);
        }
        if(price != null){
            update.set("price", price);
        }
        if(isPaid != null) {
            update.set("isPaid", isPaid);
        }
        if(isCancelled != null){
            update.set("isCancelled", isCancelled);
        }
        if(travellers != null) {
            update.set("travellers", travellers);
        }
        if(isReserved != null){
            update.set("isReserved", isReserved);
        }
        if(events != null){
            update.set("events", events);
        }

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Reservation.class);
    }


}

