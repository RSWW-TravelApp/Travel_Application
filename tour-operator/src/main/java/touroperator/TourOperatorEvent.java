package touroperator;

import events.CQRS.flights.UpdateFlightEvent;
import events.CQRS.offers.UpdateOfferEvent;
import events.Saga.TONotificationEvent;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class TourOperatorEvent {

    @Bean
    public Function<Flux<TONotificationEvent>, Mono<Void>> receivePaymentNotif() {
        return flux -> flux
                .doOnNext(event -> System.out.println("notified of purchase. I earned: " + event.getPrice() + " From offer id: " + event.getOfferId()+ " bought by user: " + event.getUser_id()))
                .then();
    }

    @PollableBean
    public Supplier<Flux<UpdateOfferEvent>> modifyOffer() {
        return () -> Flux.just(new UpdateOfferEvent("a0b4c80eff0f11ed8dc418c04",null,null,"China",null,null,null,null,null,null,null,null,null,null,null,null));
    }

    @PollableBean
    public Supplier<Flux<UpdateFlightEvent>> modifyFLight() {
        return () -> Flux.just(new UpdateFlightEvent("9f4601caff0f11ed853b18c04","China",null,null,null,null,null));
    }
}
