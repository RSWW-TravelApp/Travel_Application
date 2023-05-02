package reservationmaster;

import events.Saga.MakeReservationEvent;
import events.Saga.PayReservationEvent;
import events.Saga.RemoveReservationEvent;
import events.Saga.TONotificationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.function.Function;
import java.util.function.Supplier;
@Component
@RestController
public class ReservationMasterEvent {
    final Sinks.Many<MakeReservationEvent> sink;

    public ReservationMasterEvent() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping("/create/")
    public void makeReservationRe() {
        System.out.println("making reservation entry");
        sink.tryEmitNext(new MakeReservationEvent(123.0,"123", "123456", 59, "user_id", "offer_id", "flight_id", "Big",3,0,0,0,"yes please", 5, "2020-04-04", "2020-04-09", "false", "false"));

    }

    @Bean
    public Function<Flux<RemoveReservationEvent>, Mono<Void>> removeReservation() {
        return flux -> flux
                .doOnNext(event -> System.out.println("removing reservation:" + event.getOfferId()))
                .then();
    }
    @Bean
    public Function<Flux<PayReservationEvent>, Flux<TONotificationEvent>> finaliseReservation() {
        return flux -> flux
                .doOnNext(event -> System.out.println("marking reservation as paid for:" + event.getOfferId()))
                .map(event -> new TONotificationEvent(event.getPrice(),event.getOfferId(),event.getFlightId(),event.getSeatsNeeded()));
    }

    @Bean
    public Supplier<Flux<MakeReservationEvent>> makeReservation() {
        return sink::asFlux;
    }
}
