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
                .log().then();
    }

    @PollableBean
    public Supplier<Flux<UpdateOfferEvent>> modifyOffer() {
        return () -> Flux.just(new UpdateOfferEvent("648333e24bd14a530276af1d",null,null,"China",null,null,null,null,null,null,null,null,null,null,null,null, "true"));
    }

    @PollableBean
    public Supplier<Flux<UpdateFlightEvent>> modifyFLight() {
        return () -> Flux.just(new UpdateFlightEvent("648333ea4bd14a530276af1e","China",null,null,null,null,null, "true"));
    }
}
