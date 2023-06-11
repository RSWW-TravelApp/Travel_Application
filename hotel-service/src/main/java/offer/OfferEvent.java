package offer;

import events.CQRS.offers.CreateOfferEvent;
import events.CQRS.offers.DeleteOfferEvent;
import events.CQRS.offers.UpdateOfferEvent;
import events.Saga.ClientNotificationEvent;
import offer.data.Offer;
import offer.data.OfferService;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class OfferEvent {
    private final OfferService offerService;
    private Map<String, Integer> roomStats= new HashMap<>();
    private Map<String, Integer> hotelStats= new HashMap<>();
    public static final Sinks.Many<ClientNotificationEvent> sink_notify_client = Sinks.many().multicast().onBackpressureBuffer();
    public OfferEvent(OfferService offerService) {
        this.offerService = offerService;
    }

    @Bean
    public Function<Flux<CreateOfferEvent>, Mono<Void>> createOfferHandle() {
        return flux -> flux.doOnNext(event -> System.out.println("Create id: " + event.getId())).flatMap(
                event ->
                        offerService.createOffer(new Offer(event.getId(),
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
                                Boolean.parseBoolean(event.getAvailable())
                        ))
                )
                .log("Create Offer")
                .then();
    }

    @Bean
    public Function<Flux<DeleteOfferEvent>, Mono<Void>> deleteOfferHandle() {
        return flux -> flux.doOnNext(event -> System.out.println("Delete id: " + event.getId())).flatMap(
                event ->
                        offerService.deleteByOfferId(event.getId())
                )
                .log("Delete Offer")
                .then();
    }

    @Bean
    public Function<Flux<UpdateOfferEvent>, Mono<Void>> updateOfferHandle() {
        return flux -> flux.doOnNext(event -> {
            int topN = 5;
            System.out.println("Update id: " + event.getId());
            event.getAvailable().ifPresent(
                    available -> {
                        if (!Boolean.parseBoolean(event.getTO_generated()))
                        {
                            //Update Stats
                            offerService.findByOfferId(event.getId()).doOnNext(
                                    retrievedOffer -> {
                                        if (!Boolean.parseBoolean(available) && retrievedOffer.getAvailable())
                                        {
                                            String room_type = retrievedOffer.getRoom_type();
                                            String hotel_name = retrievedOffer.getHotel_name();
                                            if (roomStats.containsKey(room_type))
                                                roomStats.put(room_type,roomStats.get(room_type) + 1);
                                            else
                                                roomStats.put(room_type, 1);

                                            if (hotelStats.containsKey(hotel_name))
                                                hotelStats.put(hotel_name,hotelStats.get(hotel_name) + 1);
                                            else
                                                hotelStats.put(hotel_name, 1);
                                            //Check if top 10 has changed; is my new count higher than the smallest count in the top 10
                                            if (offerService.getCurrentRoomsTop10().size() < topN || roomStats.get(room_type) > offerService.getCurrentRoomsTop10().get(offerService.getCurrentRoomsTop10().size()-1).getSecond()) {
                                                List<Pair<String, Integer>> sortedRooms = roomStats.entrySet().stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).map(a -> Pair.of(a.getKey(), a.getValue())).collect(Collectors.toList());
                                                offerService.setCurrentRoomsTop10(sortedRooms.subList(0, Math.min(topN, sortedRooms.size())));
                                                sink_notify_client.tryEmitNext(new ClientNotificationEvent(
                                                        "",
                                                        "Top rooms update",
                                                        "multicast",
                                                        new HashMap<>() {{
                                                            put("top", offerService.getCurrentRoomsTop10());
                                                            put("statistics", "rooms");
                                                        }}
                                                ));
                                            }
                                            if (offerService.getCurrentHotelsTop10().size() < topN || hotelStats.get(hotel_name) > offerService.getCurrentHotelsTop10().get(offerService.getCurrentHotelsTop10().size()-1).getSecond()) {
                                                List<Pair<String, Integer>> sortedHotels = hotelStats.entrySet().stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).map(a -> Pair.of(a.getKey(), a.getValue())).collect(Collectors.toList());
                                                offerService.setCurrentHotelsTop10(sortedHotels.subList(0, Math.min(topN, sortedHotels.size())));
                                                sink_notify_client.tryEmitNext(new ClientNotificationEvent(
                                                        "",
                                                        "Top hotels update",
                                                        "multicast",
                                                        new HashMap<>() {{
                                                            put("top", offerService.getCurrentHotelsTop10());
                                                            put("statistics", "hotels");
                                                        }}
                                                ));
                                            }

                                            System.out.println(roomStats);
                                            System.out.println(hotelStats);
                                            System.out.println(offerService.getCurrentHotelsTop10String());
                                            System.out.println(offerService.getCurrentRoomsTop10String());
                                        }
                                    }
                            ).subscribe();
                        }
                    }
            );
        }).flatMap(
                        event ->
                                offerService.updateOffer(event.getId(),
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
                                        Boolean.parseBoolean(event.getAvailable().orElse(null)
                                ))
                )
                .log("Update Offer")
                .then();
    }

    @Bean
    public Supplier<Flux<ClientNotificationEvent>> notifyClient() {
        return sink_notify_client::asFlux;
    }
}
