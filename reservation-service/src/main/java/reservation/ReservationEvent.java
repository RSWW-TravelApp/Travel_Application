package reservation;

import events.CQRS.reservations.CreateReservationEvent;
import events.CQRS.reservations.DeleteReservationEvent;
import events.CQRS.reservations.UpdateReservationEvent;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reservation.data.Reservation;
import reservation.data.ReservationService;

import java.util.function.Function;

public class ReservationEvent {
    private final ReservationService reservationService;

    public ReservationEvent(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Bean
    public Function<Flux<CreateReservationEvent>, Mono<Void>> createReservationHandle() {
        return flux -> flux.doOnNext(
                        event ->
                                reservationService.createReservation(new Reservation(
                                        event.getId(),
                                        event.getUser_id(),
                                        event.getOffer_id(),
                                        event.getFlight_id(),
                                        Boolean.parseBoolean(event.getIs_paid())))
                ).then();
    }

    @Bean
    public Function<Flux<DeleteReservationEvent>, Mono<Void>> deleteReservationHandle() {
        return flux -> flux.doOnNext(
                        event ->
                                reservationService.deleteReservationById(event.getId())
                )
                .then();
    }

    @Bean
    public Function<Flux<UpdateReservationEvent>, Mono<Void>> updateReservationHandle() {
        return flux -> flux.doOnNext(
                        event ->
                                reservationService.updateReservation(event.getId(),
                                                event.getUser_id().orElse(null),
                                                event.getOffer_id().orElse(null),
                                                event.getFlight_id().orElse(null),
                                                event.getIs_paid().map(Boolean::parseBoolean).orElse(null)

                                )
                )
                .then();
    }
}