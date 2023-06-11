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
import java.util.List;
import java.util.Objects;

@Component
public class OfferWebLayerHandler {
    private final OfferService offerService;

    public OfferWebLayerHandler(OfferService offerService) {
        this.offerService = offerService;
    }

    private Integer getIntegerParam(String nameOfParam, ServerRequest request) {
        try {
            return Integer.valueOf(Objects.requireNonNull(request.queryParam(nameOfParam).orElse(null)));
        } catch (Exception ignored) {}
        return null;
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
    public Mono<ServerResponse> getHotelStats(ServerRequest request) {
        return ServerResponse.ok()
                .body(
                        Mono.just(offerService.getCurrentHotelsTop10Names()),
                        List.class
                );
    }

    public Mono<ServerResponse> getRoomStats(ServerRequest request) {
        return ServerResponse.ok()
                .body(
                        Mono.just(offerService.getCurrentRoomsTop10Names()),
                        List.class
                );
    }

    public Mono<ServerResponse> createOffer(ServerRequest request) {
        Mono<Offer> offer = request.bodyToMono(Offer.class);

        return offer
                .flatMap(offerObject -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(offerService.createOffer(offerObject), Offer.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateOfferById(ServerRequest request) {
        String id = request.pathVariable("offerId");
        Mono<Offer> updatedOffer = request.bodyToMono(Offer.class);

        return updatedOffer
                .flatMap(offer -> {
                    String hotel_name = offer.getHotel_name();
                    String image = offer.getImage();
                    String country = offer.getCountry();
                    String city = offer.getCity();
                    Integer stars = offer.getStars();
                    LocalDate start_date = offer.getStart_date();
                    LocalDate end_date = offer.getEnd_date();
                    String room_type = offer.getRoom_type();
                    Integer max_adults = offer.getMax_adults();
                    Integer max_children_to_3 = offer.getMax_children_to_3();
                    Integer max_children_to_10 = offer.getMax_children_to_10();
                    Integer max_children_to_18 = offer.getMax_children_to_18();
                    String meals = offer.getMeals();
                    Double price = offer.getPrice();
                    Boolean available = offer.getAvailable();

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

        LocalDate start_date = request.queryParam("start_date").map(LocalDate::parse).orElse(null);
        LocalDate end_date = request.queryParam("end_date").map(LocalDate::parse).orElse(null);

        Integer stars = getIntegerParam("stars", request);
        Integer max_adults = getIntegerParam("max_adults", request);
        Integer max_children_to_3 = getIntegerParam("max_children_to_3", request);
        Integer max_children_to_10 = getIntegerParam("max_children_to_10", request);
        Integer max_children_to_18 = getIntegerParam("max_children_to_18", request);
        Integer min_price = getIntegerParam("min_price", request);
        Integer max_price = getIntegerParam("max_price", request);

        Flux<Offer> offers = offerService.fetchOffers(hotel_name, image, country, city, stars, start_date, end_date,
                room_type, max_adults, max_children_to_3, max_children_to_10, max_children_to_18, meals, min_price, max_price);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(offers, Offer.class);
    }








}
