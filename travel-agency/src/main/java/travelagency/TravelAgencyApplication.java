package travelagency;


import events.Saga.MakeReservationEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;


@SpringBootApplication
public class TravelAgencyApplication {
    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        SpringApplication.run(TravelAgencyApplication.class, args);
    }
//    @Bean
//    public Function<Flux<MakeReservationEvent>, Mono<Void>> receiveReservation() {
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
//                .doOnNext(event -> System.out.println("blocking offer:" + event.getOfferId())).then();
//    }


}
