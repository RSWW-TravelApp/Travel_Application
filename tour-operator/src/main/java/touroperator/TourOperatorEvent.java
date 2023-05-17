package touroperator;

import events.Saga.TONotificationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class TourOperatorEvent {

    @Bean
    public Function<Flux<TONotificationEvent>, Mono<Void>> receivePaymentNotif() {
        return flux -> flux
                .doOnNext(event -> System.out.println("notified of purchase. I earned: " + event.getPrice() + " From offer id: " + event.getOfferId()+ " bought by user: " + event.getUser_id()))
                .then();
    }
}
