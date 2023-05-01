package flight;

import events.CQRS.flights.CreateFlightEvent;
import events.CQRS.flights.DeleteFlightEvent;
import events.CQRS.flights.UpdateFlightEvent;

import flight.data.Flight;
import flight.data.FlightService;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.function.Function;

public class FlightEvent {
    private final FlightService flightService;

    public FlightEvent(FlightService flightService) {
        this.flightService = flightService;
    }

    @Bean
    public Function<Flux<CreateFlightEvent>, Mono<Void>> createFlightHandle() {
        return flux -> flux.doOnNext(
            event ->
                flightService.createFlight(new Flight(event.getId(),
                    event.getDeparture_country(),
                    event.getDeparture_city(),
                    event.getArrival_country(),
                    event.getArrival_city(),
                    event.getAvailable_seats(),
                    LocalDate.parse(event.getDate()),
                    event.getPrice())
                )
        )
        .then();
    }

    @Bean
    public Function<Flux<DeleteFlightEvent>, Mono<Void>> deleteFlightHandle() {
        return flux -> flux.doOnNext(
            event ->
                flightService.deleteByFlightId(event.getId())
            )
        .then();
    }

    @Bean
    public Function<Flux<UpdateFlightEvent>, Mono<Void>> updateFlightHandle() {
        return flux -> flux.doOnNext(
            event ->
                flightService.updateFlight(event.getId(),
                    event.getDeparture_country().orElse(null),
                    event.getDeparture_city().orElse(null),
                    event.getArrival_country().orElse(null),
                    event.getArrival_city().orElse(null),
                    event.getAvailable_seats().orElse(0),
                    event.getDate().map(LocalDate::parse).orElse(null),
                    event.getPrice().orElse(0.0)
                )
        )
        .then();
    }
}
