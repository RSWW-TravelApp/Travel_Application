package reservationmaster.data;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ReservationMasterService {

    private ReactiveMongoTemplate reactiveMongoTemplate;
    private ReservationRepository reservationRepository;

    public ReservationMasterService(ReactiveMongoTemplate reactiveMongoTemplate, ReservationRepository reservationRepository) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.reservationRepository = reservationRepository;
    }

    public Mono<Reservation> createReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    public Flux<Reservation> getAllReservations(){
        return reservationRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Reservation> findByReservationId(String reservationID){
        return reservationRepository.findByReservationId(reservationID).switchIfEmpty(Mono.empty());
    }

    public Mono<Reservation> deleteByReservationId(String reservationID){
        return reservationRepository.findByReservationId(reservationID)
                .flatMap(existingReservation -> reservationRepository.delete(existingReservation)
                        .then(Mono.just(existingReservation)));
    }
    /*
    // updating the specific offer with the given parameters (null parameters - don't update the field)
    public Mono<Reservation> updateReservation(Reservation reservation){
        return reservationRepository.findById(reservation.getReservationID())
                .flatMap(dbReservations -> {
                    reservation.getUserID().ifPresent(dbReservations::setUserID);

                    reservation.getHotelID().ifPresent(dbReservations::setHotelID);

                    reservation.getFlightID().ifPresent(dbReservations::setFlightID);

                    reservation.getIsPaid().ifPresent(dbReservations::setIsPaid);

                    return reservationRepository.save(dbReservations);
                });
    }

*/
}
