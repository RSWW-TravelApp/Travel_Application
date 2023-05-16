package reservationmaster.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;
import reservationmaster.data.Reservation;
import reservationmaster.data.ReservationMasterService;
import reservationmaster.data.ReservationNested;

import java.util.List;

@Component
public class ReservationMasterWebLayerHandler {

    private final ReservationMasterService reservationMasterService;

    public ReservationMasterWebLayerHandler(ReservationMasterService reservationMasterService) {
        this.reservationMasterService = reservationMasterService;
    }

    public Mono<ServerResponse> getReservations(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reservationMasterService.getAllReservations(), Reservation.class);
    }

    public Mono<ServerResponse> getReservationById(ServerRequest request) {
        return reservationMasterService
                .findByReservationId(request.pathVariable("reservationId"))
                .flatMap(reservation -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(reservation), Reservation.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createReservation(ServerRequest request) {
        Mono<Reservation> reservation = request.bodyToMono(Reservation.class);

        return reservation
                .flatMap(reservationObject -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(reservationMasterService.createReservation(reservationObject), Reservation.class)
                );
    }

    public Mono<ServerResponse> deleteReservationById(ServerRequest request){
        return Mono.from(reservationMasterService.deleteReservationById(request.pathVariable("reservationId")))
                .flatMap(reservation -> ServerResponse
                        .ok()
                        .bodyValue(reservation))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

}

