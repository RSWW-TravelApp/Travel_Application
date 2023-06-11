package flight.handler;

import flight.data.Flight;
import flight.data.FlightService;
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
public class FlightWebLayerHandler {
    private final FlightService flightService;

    public FlightWebLayerHandler(FlightService flightService) {
        this.flightService = flightService;
    }

    private Integer getIntegerParam(String nameOfParam, ServerRequest request) {
        try {
            return Integer.valueOf(Objects.requireNonNull(request.queryParam(nameOfParam).orElse(null)));
        } catch (Exception ignored) {}
        return null;
    }

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

    public Mono<ServerResponse> getFlights(ServerRequest request) {
        String departure_country = request.queryParam("departure_country").orElse(null);
        String departure_city = request.queryParam("departure_city").orElse(null);
        String arrival_country = request.queryParam("arrival_country").orElse(null);
        String arrival_city = request.queryParam("arrival_city").orElse(null);

        Integer available_seats = getIntegerParam("available_seats", request);
        if (available_seats == null) {
            available_seats = 1;
        }

        LocalDate date = request.queryParam("date").map(LocalDate::parse).orElse(null);

        Flux<Flight> flights = flightService.fetchFlights(departure_country, departure_city, arrival_country,
                arrival_city, available_seats, date);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flights, Flight.class);
    }

    public Mono<ServerResponse> getDestinationStats(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Mono.just(flightService.getCurrentRoomsTop10Names()),
                        List.class
                );
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
                    String departure_country = flight.getDeparture_country();
                    String departure_city = flight.getDeparture_city();
                    String arrival_country = flight.getArrival_country();
                    String arrival_city = flight.getArrival_city();
                    Integer available_seats = flight.getAvailable_seats();
                    LocalDate date = flight.getDate();

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(
                                    flightService.updateFlight(id, departure_country, departure_city, arrival_country,
                                            arrival_city, available_seats, date),
                                    Flight.class
                            );
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteFlightById(ServerRequest request){
        return Mono.from(flightService.deleteByFlightId(request.pathVariable("flightId")))
                .flatMap(flight -> ServerResponse
                        .ok()
                        .bodyValue(flight)
                        .switchIfEmpty(ServerResponse.notFound().build()));
    }
}
