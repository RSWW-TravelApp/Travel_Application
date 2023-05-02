package reservation;

import events.CQRS.reservations.CreateReservationEvent;
import events.CQRS.reservations.DeleteReservationEvent;
import events.CQRS.reservations.UpdateReservationEvent;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
//import reservation.data.ReservationService;

import java.util.function.Function;

public class ReservationEvent {
    //private final ReservationService reservationService;

//    public ReservationEvent(ReservationService flightService) {
//        this.reservationService = flightService;
//    }

    @Bean
    public Function<Flux<CreateReservationEvent>, Mono<Void>> createReservationHandle() {
        return flux -> flux//.doOnNext(
//                        event ->
//                                flightService.createFlight(new Flight(event.getId(),
//                                        event.getAirline_name(),
//                                        event.getDeparture_country(),
//                                        event.getDeparture_city(),
//                                        event.getArrival_country(),
//                                        event.getArrival_city(),
//                                        event.getAvailable_seats(),
//                                        LocalDate.parse(event.getDate()))
//                                )
//                )
                .then();
    }

    @Bean
    public Function<Flux<DeleteReservationEvent>, Mono<Void>> deleteReservationHandle() {
        return flux -> flux//.doOnNext(
//                        event ->
//                                flightService.deleteFlightById(event.getId())
//                )
                .then();
    }

    @Bean
    public Function<Flux<UpdateReservationEvent>, Mono<Void>> updateReservationHandle() {
        return flux -> flux//.doOnNext(
//                        event ->
//                                flightService.updateFlight(new Flight(event.getId(),
//                                                event.getAirline_name().orElse(null),
//                                                event.getDeparture_country().orElse(null),
//                                                event.getDeparture_city().orElse(null),
//                                                event.getArrival_country().orElse(null),
//                                                event.getArrival_city().orElse(null),
//                                                event.getAvailable_seats().orElse(null),
//                                                event.getDate().map(LocalDate::parse).orElse(null)
//                                        )
//                                )
//                )
                .then();
    }
}