package reservation.data;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Flux<Reservation> getAllReservations(){
        return reservationRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Reservation> findByReservationId(String reservationId){
        return reservationRepository.findByReservationId(reservationId).switchIfEmpty(Mono.empty());
    }

}
