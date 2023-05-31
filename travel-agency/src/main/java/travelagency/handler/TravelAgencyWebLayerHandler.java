package travelagency.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import travelagency.data.OfferNested;
import travelagency.data.Offer;
import travelagency.data.Flight;
import travelagency.data.FlightNested;

import travelagency.data.TravelAgencyService;
import java.time.LocalDate;

@Component
public class TravelAgencyWebLayerHandler {

    private final TravelAgencyService travelAgencyService;

    public TravelAgencyWebLayerHandler(TravelAgencyService travelAgencyService) {
        this.travelAgencyService = travelAgencyService;
    }

    public Mono<ServerResponse> getOffers(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(travelAgencyService.getOffers(), Offer.class);
    }

    public Mono<ServerResponse> getFlights(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(travelAgencyService.getFlights(), Flight.class);
    }

    public Mono<ServerResponse> createOffer(ServerRequest request) {
        Mono<Offer> offer = request.bodyToMono(Offer.class);

        return offer
                .flatMap(offerObject -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(travelAgencyService.createOffer(offerObject), Offer.class)
                );
    }

    public Mono<ServerResponse> deleteOfferById(ServerRequest request){
        return Mono.from(travelAgencyService.deleteByOfferId(request.pathVariable("offerId")))
                .flatMap(offer -> ServerResponse
                        .ok()
                        .bodyValue(offer)
                        .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> addEventOffer(ServerRequest request) {
        String id = request.pathVariable("offerId");
        Mono<OfferNested> updatedOffer = request.bodyToMono(OfferNested.class);

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
                    Boolean available = offer.getAvailable().orElse(null);
                    String eventType = offer.getEventType();

                    OfferNested offerNested = new OfferNested(id, hotel_name, image, country, city, stars, start_date,
                            end_date, room_type, max_adults, max_children_to_3, max_children_to_10, max_children_to_18,
                            meals, price, available, eventType);

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(
                                    travelAgencyService.addEventOffer(offerNested),
                                    OfferNested.class
                            );
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> createFlight(ServerRequest request) {
        Mono<Flight> flight = request.bodyToMono(Flight.class);

        return flight
                .flatMap(flightObject -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(travelAgencyService.createFlight(flightObject), Flight.class)
                );
    }

    public Mono<ServerResponse> deleteFlightById(ServerRequest request){
        return Mono.from(travelAgencyService.deleteByFlightId(request.pathVariable("flightId")))
                .flatMap(flight -> ServerResponse
                        .ok()
                        .bodyValue(flight)
                        .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> addEventFlight(ServerRequest request) {
        String id = request.pathVariable("flightId");
        Mono<FlightNested> updatedFlight = request.bodyToMono(FlightNested.class);

        return updatedFlight
                .flatMap(flight -> {
                    String departure_country = flight.getDeparture_country().orElse(null);
                    String departure_city = flight.getDeparture_city().orElse(null);
                    String arrival_country = flight.getArrival_country().orElse(null);
                    String arrival_city = flight.getArrival_city().orElse(null);
                    Integer available_seats = flight.getAvailable_seats().orElse(null);
                    LocalDate date = flight.getDate().orElse(null);
                    String eventType = flight.getEventType();

                    FlightNested flightNested = new FlightNested(id, departure_country, departure_city, arrival_country,
                            arrival_city, available_seats, date, eventType);

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(
                                    travelAgencyService.addEventFlight(flightNested),
                                    FlightNested.class
                            );
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }






}
