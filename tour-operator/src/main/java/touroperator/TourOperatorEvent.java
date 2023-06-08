package touroperator;

import events.CQRS.flights.UpdateFlightEvent;
import events.CQRS.offers.UpdateOfferEvent;
import events.Saga.TONotificationEvent;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class TourOperatorEvent {
    private final Random rand = new Random();

    @Bean
    public Function<Flux<TONotificationEvent>, Mono<Void>> receivePaymentNotif() {
        return flux -> flux
                .doOnNext(event -> System.out.println("notified of purchase. I earned: " + event.getPrice() + " From offer id: " + event.getOfferId()+ " bought by user: " + event.getUser_id()))
                .then();
    }

    @PollableBean
    public Supplier<Flux<UpdateOfferEvent>> modifyOffer() {
        return () -> {
            List<String> givenList = Arrays.asList("Kazakhstan", "Japan", "Tajikistan", "Russia", "England", "Arabia", "France", "Germany", "China", "Spain");
            String randomElement = givenList.get(rand.nextInt(givenList.size()));
            System.out.println(randomElement);
            return Flux.just(new UpdateOfferEvent("a0b4c80eff0f11ed8dc418c04",null,null,randomElement,null,null,null,null,null,null,null,null,null,null,null,null));
        };
    }

    @PollableBean
    public Supplier<Flux<UpdateFlightEvent>> modifyFLight() {
        return () -> {
            List<String> givenList = Arrays.asList("Kazakhstan", "Japan", "Tajikistan", "Russia", "England", "Arabia", "France", "Germany", "China", "Spain");
            String randomElement = givenList.get(rand.nextInt(givenList.size()));
            System.out.println(randomElement);
            return Flux.just(new UpdateFlightEvent("9ef65b0aff0f11edaead18c04",randomElement,null,null,null,null,null));
        };
    }
}
