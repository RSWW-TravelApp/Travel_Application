package reservation.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reservation.data.Reservation;
import reservation.data.ReservationService;

@Component
public class ReservationWebLayerHandler {

    private final ReservationService reservationService;

    public ReservationWebLayerHandler(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public Mono<ServerResponse> getReservations(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reservationService.getAllReservations(), Reservation.class);
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

