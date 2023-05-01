package flight.handler;

import flight.data.Flight;
import flight.data.FlightService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
public class FlightWebLayerHandler {
    private final FlightService flightService;

    public FlightWebLayerHandler(FlightService flightService) {
        this.flightService = flightService;
    }

    @NotNull
    public Mono<ServerResponse> getAllFlights(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flightService.getAllFlights(), Flight.class);
    }

    public Mono<ServerResponse> getFlightById(ServerRequest request) {
        return flightService
                .findByFlightId(request.pathVariable("flightId"))
                .flatMap(flight -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(flight), Flight.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createFlight(ServerRequest request) {
        Mono<Flight> flight = request.bodyToMono(Flight.class);

        return flight
                .flatMap(flightObject -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(flightService.createFlight(flightObject), Flight.class)
                );
    }

    public Mono<ServerResponse> updateFlightById(ServerRequest request) {
        String id = request.pathVariable("flightId");
        Mono<Flight> updatedFlight = request.bodyToMono(Flight.class);

        return updatedFlight
                .flatMap(flight -> {
                    String departure_country = flight.getDeparture_country().orElse(null);
                    String departure_city = flight.getDeparture_city().orElse(null);
                    String arrival_country = flight.getArrival_country().orElse(null);
                    String arrival_city = flight.getArrival_city().orElse(null);
                    Integer available_seats = flight.getAvailable_seats().orElse(0);
                    LocalDate date = flight.getDate().orElse(null);
                    Double price = flight.getPrice().orElse(0.0);

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(
                                    flightService.updateFlight(id, departure_country, departure_city, arrival_country,
                                            arrival_city, available_seats, date, price),
                                    Flight.class
                            );
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteFlightById(ServerRequest request){
        return flightService.deleteByFlightId(request.pathVariable("flightId"))
                .flatMap(flight -> ServerResponse.ok().body(flight, Flight.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
