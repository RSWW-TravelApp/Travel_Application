package travelagency;

import events.Saga.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import java.util.function.Function;
@Component
public class TravelAgencyEvent {

    @Bean
    public Function<Flux<MakeReservationEvent>, Flux<SagaEvent>> blockResources() {
        return flux -> flux
                .doOnNext(event -> System.out.println("blocking offer:" + event.getOfferId()))
                .map(event -> new RequirePaymentEvent(20.0,event.getUser_id(), event.getOfferId(),event.getFlightId(),1337));
    }

//    @Bean
//    public Function<Flux<RequirePaymentEvent>, Flux<RequirePaymentEvent>> receiveReservation2() {
//        return flux -> flux
//                .doOnNext(event -> System.out.println("blocking offer2:" + event.getOfferId()));
//        //.map(event -> new MakeReservationEvent(123.0,"123", "123456", 59,"Big",3,0,0,0,"yes please", 5, "2020-04-04", "2020-04-09", "false", "false"));
//
//    }


    @Bean
    public Function<Flux<UnblockResourcesEvent>, Flux<RemoveReservationEvent>> unblockResources() {
//        return flux -> flux.doOnNext(event -> System.out.println("U" + (new Flight(event.getId(),
//                event.getAirline_name().orElse(null),
//                event.getDeparture_country().orElse(null),
//                event.getDeparture_city().orElse(null),
//                event.getArrival_country().orElse(null),
//                event.getArrival_city().orElse(null),
//                event.getAvailable_seats().orElse(null),
//                event.getDate().map(LocalDate::parse).orElse(null)
//        )))).then();
        return flux -> flux
                .doOnNext(event -> System.out.println("unblocking resources:" + event.getOfferId()))
                .map(event -> new RemoveReservationEvent(event.getPrice(), event.getUser_id(), event.getOfferId(),event.getFlightId(),1337));
    }


//    @Bean
//    public Function<Flux<String>,Mono<Void>> receiveReservation() {
////        return flux -> flux.doOnNext(event -> System.out.println("U" + (new Flight(event.getId(),
////                event.getAirline_name().orElse(null),
////                event.getDeparture_country().orElse(null),
////                event.getDeparture_city().orElse(null),
////                event.getArrival_country().orElse(null),
////                event.getArrival_city().orElse(null),
////                event.getAvailable_seats().orElse(null),
////                event.getDate().map(LocalDate::parse).orElse(null)
////        )))).then();
//        return flux -> flux
//                .doOnNext(event -> System.out.println("blocking offer:" + event.toUpperCase(Locale.ROOT)))
//                .then();
//    }
}

