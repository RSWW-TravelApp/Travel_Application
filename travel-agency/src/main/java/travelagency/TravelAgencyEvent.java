package travelagency;

import events.CQRS.flights.CreateFlightEvent;
import events.CQRS.flights.DeleteFlightEvent;
import events.CQRS.flights.UpdateFlightEvent;
import events.CQRS.offers.CreateOfferEvent;
import events.CQRS.offers.DeleteOfferEvent;
import events.CQRS.offers.UpdateOfferEvent;
import events.CQRS.reservations.CreateReservationEvent;
import events.CQRS.reservations.DeleteReservationEvent;
import events.CQRS.reservations.UpdateReservationEvent;
import events.Saga.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import travelagency.data.Flight;
import travelagency.data.FlightNested;
import travelagency.data.OfferNested;
import travelagency.data.TravelAgencyService;

import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class TravelAgencyEvent {
    private final TravelAgencyService travelAgencyService;
    public static final Sinks.Many<RemoveReservationEvent> sink_cancelled_reservations = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<CreateFlightEvent> sink_CQRS_flights_create = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<DeleteFlightEvent> sink_CQRS_flights_delete = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<UpdateFlightEvent> sink_CQRS_flights_update = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<CreateOfferEvent> sink_CQRS_offers_create = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<DeleteOfferEvent> sink_CQRS_offers_delete = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<UpdateOfferEvent> sink_CQRS_offers_update = Sinks.many().multicast().onBackpressureBuffer();
    public TravelAgencyEvent(TravelAgencyService travelAgencyService) {
        this.travelAgencyService = travelAgencyService;
    }
    @Bean
    public Function<Flux<BlockResourcesEvent>, Flux<RequirePaymentEvent>> blockResources() {
        return flux -> flux
                .doOnNext(event -> {
                        System.out.println("attempting to block offer:" + event.getOfferId());
                        travelAgencyService.addEventOffer(new OfferNested(
                                event.getOfferId(),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                false,
                                "BlockResourcesEvent"))
                                //.switchIfEmpty(Mono.empty())
                                .doOnNext(a -> System.out.println("attempting to block flight:" + event.getFlight_id()))
                                .flatMap(a ->
                                    travelAgencyService.addEventFlight(new FlightNested(
                                            event.getFlight_id(),
                                            null,
                                            null,
                                            null,
                                            null,
                                            event.getSeatsNeeded(),
                                            null,
                                            "BlockResourcesEvent"))).doOnNext(a ->  event.setSuccess(true))
                                .switchIfEmpty(travelAgencyService.addEventOffer(new OfferNested(
                                        event.getOfferId(),
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        true,
                                        "UnblockResourcesEvent")).flatMap(a -> Mono.empty()))
                                .block();
                })
                .filter(BlockResourcesEvent::getSuccess)
                .doOnDiscard(BlockResourcesEvent.class, a -> {
                    System.out.println("Reservation of Resources failed!");
                    sink_cancelled_reservations.tryEmitNext(new RemoveReservationEvent(a.getPrice(),a.getUser_id(),a.getOfferId(),a.getFlight_id(),a.getPayment_id(),a.getReservation_id(),a.getSeatsNeeded()));
                })
                .doOnNext(a -> {
                    System.out.println("Reservation of Resources, successful, pushing events.");
                })
                .map(event -> new RequirePaymentEvent(event.getPrice(),event.getUser_id(), event.getOfferId(),event.getFlight_id(),event.getPayment_id(),event.getReservation_id(),event.getSeatsNeeded()));
    }

//    @Bean
//    public Function<Flux<RequirePaymentEvent>, Flux<RequirePaymentEvent>> receiveReservation2() {
//        return flux -> flux
//                .doOnNext(event -> System.out.println("blocking offer2:" + event.getOfferId()));
//        //.map(event -> new MakeReservationEvent(123.0,"123", "123456", 59,"Big",3,0,0,0,"yes please", 5, "2020-04-04", "2020-04-09", "false", "false"));
//
//    }


    @Bean
    public Function<Flux<UnblockResourcesEvent>, Flux<RemoveReservationEvent>> unblockResources() {
        return flux -> flux
                .doOnNext(event -> {
                    System.out.println("unblocking offer:" + event.getOfferId());
                    travelAgencyService.addEventOffer(new OfferNested(
                            event.getOfferId(),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            true,
                            "UnblockResourcesEvent")).switchIfEmpty(Mono.empty()).subscribe();

                    System.out.println("unblocking flight:" + event.getFlight_id());
                    travelAgencyService.addEventFlight(new FlightNested(
                            event.getFlight_id(),
                            null,
                            null,
                            null,
                            null,
                            event.getSeatsNeeded(),
                            null,
                            "UnblockResourcesEvent")).switchIfEmpty(Mono.empty()
                    ).subscribe();
                })
                .map(event -> new RemoveReservationEvent(event.getPrice(),event.getUser_id(), event.getOfferId(),event.getFlight_id(),event.getPayment_id(),event.getReservation_id(),event.getSeatsNeeded()));
    }

    @Bean
    public Supplier<Flux<RemoveReservationEvent>> cancelReservation() {
        return sink_cancelled_reservations::asFlux;
    }
    @Bean
    public Supplier<Flux<CreateFlightEvent>> createFlightCQRSHandle() {
        return sink_CQRS_flights_create::asFlux;
    }
    @Bean
    public Supplier<Flux<DeleteFlightEvent>> deleteFlightCQRSHandle() {
        return sink_CQRS_flights_delete::asFlux;
    }
    @Bean
    public Supplier<Flux<UpdateFlightEvent>> updateFlightCQRSHandle() {
        return sink_CQRS_flights_update::asFlux;
    }
    @Bean
    public Supplier<Flux<CreateOfferEvent>> createOfferCQRSHandle() {
        return sink_CQRS_offers_create::asFlux;
    }
    @Bean
    public Supplier<Flux<DeleteOfferEvent>> deleteOfferCQRSHandle() {
        return sink_CQRS_offers_delete::asFlux;
    }
    @Bean
    public Supplier<Flux<UpdateOfferEvent>> updateOfferCQRSHandle() {
        return sink_CQRS_offers_update::asFlux;
    }
//    @Bean
//    public Function<Flux<String>,Mono<Void>> receiveReservation() {
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
//                .doOnNext(event -> System.out.println("blocking offer:" + event.toUpperCase(Locale.ROOT)))
//                .then();
//    }
}

