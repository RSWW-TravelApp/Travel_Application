package reservation;

import events.CQRS.reservations.CreateReservationEvent;
import events.CQRS.reservations.DeleteReservationEvent;
import events.CQRS.reservations.UpdateReservationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reservation.data.Reservation;
import reservation.data.ReservationService;

import java.util.function.Function;

@Component
public class ReservationEvent {
    private final ReservationService reservationService;

    public ReservationEvent(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Bean
    public Function<Flux<CreateReservationEvent>, Mono<Void>> createReservationHandle() {
        return flux -> flux.doOnNext(a -> System.out.println("CREATE id: " + a.getId())).flatMap(
                            event ->
                                reservationService.createReservation(new Reservation(
                                    event.getId(),
                                    event.getUser_id(),
                                    event.getOffer_id(),
                                    event.getFlight_id(),
                                    Boolean.parseBoolean(event.getIs_paid()),
                                    Boolean.parseBoolean(event.getIsCancelled()),
                                    event.getPrice(),
                                    event.getTravellers(),
                                    event.getPaymentId(),
                                    Boolean.parseBoolean(event.getIsReserved()))
                                )).then();
    }

    @Bean
    public Function<Flux<DeleteReservationEvent>, Mono<Void>> deleteReservationHandle() {
        return flux -> flux.flatMap(
                        event ->
                                reservationService.deleteReservationById(event.getId())
                )
                .then();
    }

    @Bean
    public Function<Flux<UpdateReservationEvent>, Mono<Void>> updateReservationHandle() {
        return flux -> flux.doOnNext(a -> System.out.println("Update id: " + a.getId())).flatMap(
                        event ->
                                reservationService.updateReservation(event.getId(),
                                    event.getUser_id().orElse(null),
                                    event.getOffer_id().orElse(null),
                                    event.getFlight_id().orElse(null),
                                    event.getIs_paid().map(Boolean::parseBoolean).orElse(null),
                                    event.getIsCancelled().map(Boolean::parseBoolean).orElse(null),
                                    event.getPrice().orElse(null),
                                    event.getTravellers().orElse(null),
                                    event.getPaymentId().orElse(null),
                                    event.getIsReserved().map(Boolean::parseBoolean).orElse(null)

                                )
                )
                .doOnNext(a -> System.out.println("DID IT WORK?"))
                .then();
    }
}