package offer.handler;

import offer.data.OfferService;
import offer.data.Offer;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class OfferWebLayerHandler {
    private final OfferService offerService;

    public OfferWebLayerHandler(OfferService offerService) {
        this.offerService = offerService;
    }

    public Mono<ServerResponse> getAllOffers(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(offerService.getAllOffers(), Offer.class);
    }

    public Mono<ServerResponse> getOfferById(ServerRequest request) {
        return offerService
                .findByOfferId(request.pathVariable("offerId"))
                .flatMap(offer -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(offer, Offer.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createOffer(ServerRequest request) {
        Mono<Offer> offer = request.bodyToMono(Offer.class);

        return offer
                .flatMap(offerObject -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(offerService.createOffer(offerObject), Offer.class)
                );
    }


    public Mono<ServerResponse> updateOfferById(ServerRequest request) {
        String id = request.pathVariable("offerId");
        Mono<Offer> updatedOffer = request.bodyToMono(Offer.class);
        return updatedOffer
                .flatMap(offer -> {
                    String hotel_name = offer.getHotel_name().orElse(null);
                    String image = offer.getImage().orElse(null);
                    String country = offer.getCountry().orElse(null);
                    String city = offer.getCity().orElse(null);
                    int stars = offer.getStars().orElse(0);
                    String room_type = offer.getRoom_type().orElse(null);
                    int max_adults = offer.getMax_adults().orElse(0);
                    int max_children_to_3 = offer.getMax_children_to_3().orElse(0);
                    int max_children_to_10 = offer.getMax_children_to_10().orElse(0);
                    int max_children_to_18 = offer.getMax_children_to_18().orElse(0);
                    String meals = offer.getMeals().orElse(null);
                    double price = offer.getPrice().orElse(0.0);
                    boolean available = offer.getAvailable().orElse(true);

                    System.out.println(room_type + max_adults + max_children_to_3 + max_children_to_10 +
                            max_children_to_18 + meals + price + available);

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(
                                    offerService.updateOffer(id, hotel_name, image, country, city, stars, room_type,
                                            max_adults, max_children_to_3, max_children_to_10, max_children_to_18,
                                            meals, price, available),
                                    Offer.class
                            );
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    // TODO fix delete
    public Mono<ServerResponse> deleteOfferById(ServerRequest request){
        return offerService.deleteByOfferId(request.pathVariable("offerId"))
                .flatMap(offer -> ServerResponse.ok().body(offer, Offer.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
