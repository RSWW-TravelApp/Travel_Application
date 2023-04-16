package reservation;

import reservation.data.Reservation;
import reservation.data.ReservationRepository;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@Service
public class ReservationService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final ReservationRepository reservationRepository;

    public ReservationService(ReactiveMongoTemplate reactiveMongoTemplate, ReservationRepository reservationRepository) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.reservationRepository = reservationRepository;
    }

    public Mono<Reservation> createReservation(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    public Flux<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public Mono<Reservation> findById(String reservationID){
        return reservationRepository.findById(reservationID);
    }

    public Mono<Reservation> deleteReservation(String reservationID){
        return reservationRepository.findById(reservationID)
                .flatMap(existingReservation -> reservationRepository.delete(existingReservation)
                        .then(Mono.just(existingReservation)));
    }

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


}
