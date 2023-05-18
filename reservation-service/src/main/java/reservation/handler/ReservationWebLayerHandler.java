package reservation.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reservation.data.Reservation;
import reservation.data.ReservationService;

import java.time.LocalDate;

@Component
public class ReservationWebLayerHandler {

    private final ReservationService reservationService;

    public ReservationWebLayerHandler(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    private Boolean getBooleanParameter(String name, ServerRequest request) {
        String boolStr = request.queryParam(name).orElse(null);
        if (boolStr == null) {
            return null;
        }
        return Boolean.parseBoolean(request.queryParam("isPaid").orElse(null));
    }

    public Mono<ServerResponse> getReservations(ServerRequest request) {
        String userId = request.queryParam("userId").orElse(null);
        String flightId = request.queryParam("flightId").orElse(null);
        String offerId = request.queryParam("offerId").orElse(null);
        Boolean isPaid = getBooleanParameter("isPaid", request);
        Boolean isCancelled = getBooleanParameter("isCancelled", request);

        Flux<Reservation> offers = reservationService.fetchReservations(userId, flightId, offerId, isPaid, isCancelled);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(offers, Reservation.class);
    }

    public Mono<ServerResponse> getReservationsByUserId(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reservationService.getAllByUserId(request.pathVariable("userId")), Reservation.class);
    }

    public Mono<ServerResponse> getReservationById(ServerRequest request) {
        return reservationService
                .findByReservationId(request.pathVariable("reservationId"))
                .flatMap(reservation -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(reservation), Reservation.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}

