package flight;

import Events.Flights.CreateFlightEvent;
import Events.Flights.DeleteFlightEvent;
import Events.Flights.UpdateFlightEvent;

import flight.data.Flight;
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
                    event.getAirline_name(),
                    event.getDeparture_country(),
                    event.getDeparture_city(),
                    event.getArrival_country(),
                    event.getArrival_city(),
                    event.getAvailable_seats(),
                    LocalDate.parse(event.getDate()))
                )
        )
        .then();
    }

    @Bean
    public Function<Flux<DeleteFlightEvent>, Mono<Void>> deleteFlightHandle() {
        return flux -> flux.doOnNext(
            event ->
                flightService.deleteFlight(event.getId())
            )
        .then();
    }

    @Bean
    public Function<Flux<UpdateFlightEvent>, Mono<Void>> updateFlightHandle() {
        return flux -> flux.doOnNext(
            event ->
                flightService.updateFlight(new Flight(event.getId(),
                    event.getAirline_name().orElse(null),
                    event.getDeparture_country().orElse(null),
                    event.getDeparture_city().orElse(null),
                    event.getArrival_country().orElse(null),
                    event.getArrival_city().orElse(null),
                    event.getAvailable_seats().orElse(null),
                    event.getDate().map(LocalDate::parse).orElse(null)
                    )
                )
        )
        .then();
    }
}
