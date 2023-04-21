package offer;

import Events.CQRS.Offers.CreateOfferEvent;
import Events.CQRS.Offers.DeleteOfferEvent;
import Events.CQRS.Offers.UpdateOfferEvent;
import offer.data.Offer;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.function.Function;

public class OfferEvent {
    private final OfferService offerService;

    public OfferEvent(OfferService offerService) {
        this.offerService = offerService;
    }

    @Bean
    public Function<Flux<CreateOfferEvent>, Mono<Void>> createOfferHandle() {
        return flux -> flux.doOnNext(
                event ->
                        offerService.updateOffer(new Offer(event.getId(),
                                event.getHotel_name(),
                                event.getImage(),
                                event.getCountry(),
                                event.getCity(),
                                event.getStars(),
                                LocalDate.parse(event.getStart_date()),
                                LocalDate.parse(event.getEnd_date()),
                                event.getRoom_type(),
                                event.getMax_adults(),
                                event.getMax_children_to_3(),
                                event.getMax_children_to_10(),
                                event.getMax_children_to_18(),
                                event.getMeals(),
                                event.getPrice(),
                                event.getAvailable()
                        ))
                )
                .then();
    }

    @Bean
    public Function<Flux<DeleteOfferEvent>, Mono<Void>> deleteOfferHandle() {
        return flux -> flux.doOnNext(
                event ->
                        offerService.deleteOffer(event.getId())
                )
                .then();
    }

    @Bean
    public Function<Flux<UpdateOfferEvent>, Mono<Void>> updateOfferHandle() {
        return flux -> flux.doOnNext(
                        event ->
                                offerService.updateOffer(new Offer(event.getId(),
                                        event.getHotel_name().orElse(null),
                                        event.getImage().orElse(null),
                                        event.getCountry().orElse(null),
                                        event.getCity().orElse(null),
                                        event.getStars().orElse(null),
                                        event.getStart_date().map(LocalDate::parse).orElse(null),
                                        event.getEnd_date().map(LocalDate::parse).orElse(null),
                                        event.getRoom_type().orElse(null),
                                        event.getMax_adults().orElse(null),
                                        event.getMax_children_to_3().orElse(null),
                                        event.getMax_children_to_10().orElse(null),
                                        event.getMax_children_to_18().orElse(null),
                                        event.getMeals().orElse(null),
                                        event.getPrice().orElse(null),
                                        event.getAvailable().orElse(null)
                                ))
                )
                .then();
    }
}
