package reservation.handler;

import org.springframework.http.HttpStatus;
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

    public Mono<ServerResponse> createReservation(ServerRequest request) {
        Mono<Reservation> reservation = request.bodyToMono(Reservation.class);

        return reservation
                .flatMap(reservationObject -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(reservationService.createReservation(reservationObject), Reservation.class)
                );
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

    public Mono<ServerResponse> updateReservationById(ServerRequest request) {
        String id = request.pathVariable("reservationId");
        Mono<Reservation> updatedReservation = request.bodyToMono(Reservation.class);

        return updatedReservation
                .flatMap(reservation -> {
                    String userId = reservation.getUserId().orElse(null);
                    String flightId = reservation.getFlightId().orElse(null);
                    String offerId = reservation.getOfferId().orElse(null);
                    boolean isPaid = reservation.getIsPaid().orElse(false);

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(
                                    reservationService.updateReservation(id, userId, flightId, offerId, isPaid),
                                    Reservation.class
                            );
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteReservationById(ServerRequest request){
        return Mono.from(reservationService.deleteReservationById(request.pathVariable("reservationId")))
                .flatMap(reservation -> ServerResponse
                        .ok()
                        .bodyValue(reservation))
                .switchIfEmpty(ServerResponse.notFound().build());

    }




}

