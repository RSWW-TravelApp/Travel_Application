package offer.handler;

import offer.data.Offer;
import offer.data.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

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
                        .body(Mono.just(offer), Offer.class)
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
                    Integer stars = offer.getStars().orElse(null);
                    LocalDate start_date = offer.getStart_date().orElse(null);
                    LocalDate end_date = offer.getEnd_date().orElse(null);
                    String room_type = offer.getRoom_type().orElse(null);
                    Integer max_adults = offer.getMax_adults().orElse(null);
                    Integer max_children_to_3 = offer.getMax_children_to_3().orElse(null);
                    Integer max_children_to_10 = offer.getMax_children_to_10().orElse(null);
                    Integer max_children_to_18 = offer.getMax_children_to_18().orElse(null);
                    String meals = offer.getMeals().orElse(null);
                    Double price = offer.getPrice().orElse(null);
                    boolean available = offer.getAvailable().orElse(true);

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(
                                    offerService.updateOffer(id, hotel_name, image, country, city, stars, start_date,
                                            end_date, room_type, max_adults, max_children_to_3, max_children_to_10, max_children_to_18,
                                            meals, price, available),
                                    Offer.class
                            );
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteOfferById(ServerRequest request){
        return Mono.from(offerService.deleteByOfferId(request.pathVariable("offerId")))
                .flatMap(offer -> ServerResponse
                        .ok()
                        .bodyValue(offer)
                .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> getOffers(ServerRequest request) {
        String hotel_name = request.queryParam("hotel_name").orElse(null);
        String image = request.queryParam("image").orElse(null);
        String country = request.queryParam("country").orElse(null);
        String city = request.queryParam("city").orElse(null);
        String room_type = request.queryParam("room_type").orElse(null);
        String meals = request.queryParam("meals").orElse(null);

        LocalDate start_date = LocalDate.parse(request.queryParam("start_date").orElse("2019-01-01"));
        LocalDate end_date = LocalDate.parse(request.queryParam("end_date").orElse("2026-01-01"));

        Integer stars = Integer.getInteger(request.queryParam("stars").orElse(null));
        Integer max_adults = Integer.getInteger(request.queryParam("max_adults").orElse(null));
        Integer max_children_to_3 = Integer.getInteger(request.queryParam("max_children_to_3").orElse(null));
        Integer max_children_to_10 = Integer.getInteger(request.queryParam("max_children_to_10").orElse(null));
        Integer max_children_to_18 = Integer.getInteger(request.queryParam("max_children_to_18").orElse(null));

        Flux<Offer> offers = offerService.fetchOffers(hotel_name, image, country, city, stars, start_date, end_date,
                room_type, max_adults, max_children_to_3, max_children_to_10, max_children_to_18, meals);

        // For testing purpose
        // offers.doOnNext(element -> System.out.println("Element: " + element))
        //        .subscribe();

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(offers, Offer.class);
    }








}
