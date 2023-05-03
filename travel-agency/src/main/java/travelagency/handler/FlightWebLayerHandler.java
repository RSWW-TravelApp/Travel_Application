package travelagency.handler;

import travelagency.data.Flight;
import travelagency.data.TravelAgencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import travelagency.data.TravelAgencyService;

import java.time.LocalDate;

@Component
public class FlightWebLayerHandler {
    private final TravelAgencyService travelAgencyService;

    public FlightWebLayerHandler(TravelAgencyService travelAgencyService) {
        this.travelAgencyService = travelAgencyService;
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

    public Mono<ServerResponse> updateFlightById(ServerRequest request) {
        String id = request.pathVariable("flightId");
        Mono<Flight> updatedFlight = request.bodyToMono(Flight.class);

        return updatedFlight
                .flatMap(flight -> {
                    String departure_country = flight.getDeparture_country().orElse(null);
                    String departure_city = flight.getDeparture_city().orElse(null);
                    String arrival_country = flight.getArrival_country().orElse(null);
                    String arrival_city = flight.getArrival_city().orElse(null);
                    Integer available_seats = flight.getAvailable_seats().orElse(null);
                    LocalDate date = flight.getDate().orElse(null);

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(travelAgencyService.updateFlight(id, departure_country, departure_city, arrival_country,
                                            arrival_city, available_seats, date),
                                    Flight.class
                            );
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteFlightById(ServerRequest request){
        return Mono.from(travelAgencyService.deleteByFlightId(request.pathVariable("flightId")))
                .flatMap(flight -> ServerResponse
                        .ok()
                        .bodyValue(flight)
                        .switchIfEmpty(ServerResponse.notFound().build()));
    }
}
