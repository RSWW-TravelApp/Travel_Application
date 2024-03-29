package flight;

import events.CQRS.flights.CreateFlightEvent;
import events.CQRS.flights.DeleteFlightEvent;
import events.CQRS.flights.UpdateFlightEvent;

import events.Saga.ClientNotificationEvent;
import flight.data.Flight;
import flight.data.FlightService;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class FlightEvent {
    private final FlightService flightService;
    private Map<String, Integer> destinationStats= new HashMap<>();
    public static final Sinks.Many<ClientNotificationEvent> sink_notify_client = Sinks.many().multicast().onBackpressureBuffer();
    public FlightEvent(FlightService flightService) {
        this.flightService = flightService;
    }

    @Bean
    public Function<Flux<CreateFlightEvent>, Mono<Void>> createFlightHandle() {
        return flux -> flux.doOnNext(event -> System.out.println("Create id: " + event.getId())).flatMap(
            event ->
                flightService.createFlight(new Flight(event.getId(),
                    event.getDeparture_country(),
                    event.getDeparture_city(),
                    event.getArrival_country(),
                    event.getArrival_city(),
                    event.getAvailable_seats(),
                    LocalDate.parse(event.getDate()))
                )
        )
        .log("Create Flight")
        .then();
    }

    @Bean
    public Function<Flux<DeleteFlightEvent>, Mono<Void>> deleteFlightHandle() {
        return flux -> flux.doOnNext(event -> System.out.println("Delete id: " + event.getId())).flatMap(
            event ->
                flightService.deleteByFlightId(event.getId())
            )
        .log("Delete Flight")
        .then();
    }

    @Bean
    public Function<Flux<UpdateFlightEvent>, Mono<Void>> updateFlightHandle() {
        return flux -> flux.doOnNext(event -> {
            int topN = 5;
            System.out.println("Update id: " + event.getId());
            event.getAvailable_seats().ifPresent(
                    seats -> {
                        if (!Boolean.parseBoolean(event.getTO_generated()))
                        {
                            flightService.findByFlightId(event.getId()).doOnNext(
                                    retrievedFlight -> {
                                        // seats have decreased, we have made a reservation
                                        if ( retrievedFlight.getAvailable_seats() > event.getAvailable_seats().get())
                                        {
                                            String destination = retrievedFlight.getArrival_country();
                                            if (destinationStats.containsKey(destination))
                                                destinationStats.put(destination, destinationStats.get(destination) + 1);
                                            else
                                                destinationStats.put(destination, 1);
                                            if (flightService.getCurrentDestinationsTop10().size() < topN || destinationStats.get(destination) > flightService.getCurrentDestinationsTop10().get(flightService.getCurrentDestinationsTop10().size()-1).getSecond()) {
                                                List<Pair<String, Integer>> sortedRooms = destinationStats.entrySet().stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).map(a -> Pair.of(a.getKey(), a.getValue())).collect(Collectors.toList());
                                                flightService.setCurrentDestinationsTop10(sortedRooms.subList(0, Math.min(topN, sortedRooms.size())));
                                                sink_notify_client.tryEmitNext(new ClientNotificationEvent(
                                                        "",
                                                        "Top destinations update",
                                                        "multicast",
                                                        new HashMap<>() {{
                                                            put("top", flightService.getCurrentDestinationsTop10());
                                                            put("statistics", "destinations");
                                                        }}
                                                ));
                                            }
                                            System.out.println(destinationStats);
                                            System.out.println(flightService.getCurrentDestinationsTop10String());
                                        }
                                    }
                            ).subscribe();
                        }
                    }
            );
        }).flatMap(
            event ->
                flightService.updateFlight(event.getId(),
                    event.getDeparture_country().orElse(null),
                    event.getDeparture_city().orElse(null),
                    event.getArrival_country().orElse(null),
                    event.getArrival_city().orElse(null),
                    event.getAvailable_seats().orElse(null),
                    event.getDate().map(LocalDate::parse).orElse(null)
                )
        )
        .log("Update Flight")
        .then();
    }

    @Bean
    public Supplier<Flux<ClientNotificationEvent>> notifyClient() {
        return sink_notify_client::asFlux;
    }
}
