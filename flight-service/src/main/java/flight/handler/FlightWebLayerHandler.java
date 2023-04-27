package flight.handler;

import flight.data.Flight;
import flight.data.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class FlightWebLayerHandler {
    private final FlightService flightService;

    public FlightWebLayerHandler(FlightService flightService) {
        this.flightService = flightService;
    }

    public Mono<ServerResponse> getAllFlights(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flightService.getAllFlights(), Flight.class);
    }

    public Mono<ServerResponse> getFlightById(ServerRequest request) {
        return flightService
                .findFlightById(request.pathVariable("flightId"))
                .flatMap(flight -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(flight, Flight.class)
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
                .flatMap(flightObject -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(flightService.updateFlight(flightObject), Flight.class)
                );
    }

    public Mono<ServerResponse> deleteFlightById(ServerRequest request){
        return flightService.deleteFlightById(request.pathVariable("flightId"))
                .flatMap(flight -> ServerResponse.ok().body(flight, Flight.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
