package reservationmaster.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reservationmaster.data.Reservation;
import reservationmaster.data.ReservationMasterService;

@Component
public class ReservationMasterWebLayerHandler {
    private final ReservationMasterService reservationMasterService;

    public ReservationMasterWebLayerHandler(ReservationMasterService reservationMasterService) {
        this.reservationMasterService = reservationMasterService;
    }

    public Mono<ServerResponse> makeReservation(ServerRequest request) {
        Mono<Reservation> reservation = request.bodyToMono(Reservation.class);

        return reservation
                .flatMap(reservationObject -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(reservationMasterService.createReservation(reservationObject), Reservation.class)
                );
    }

    public Mono<ServerResponse> updateReservation(ServerRequest request) {
        String id = request.pathVariable("reservationId");
        Mono<Reservation> updatedReservation = request.bodyToMono(Reservation.class);
        return updatedReservation
                .flatMap(reservation -> {
                    String hotelId = reservation.getHotelId().orElse(null);
                    String flightId = reservation.getFlightId().orElse(null);
                    String userId = reservation.getUserId().orElse(null);
                    Boolean isPaid = reservation.getIsPaid().orElse(null);

                    return ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(reservationMasterService.updateReservation(id, userId, flightId, hotelId, isPaid), Reservation.class);
                });
    }

    public Mono<ServerResponse> deleteReservation(ServerRequest request){
        return Mono.from(reservationMasterService.deleteByReservationId(request.pathVariable("reservationId")))
                .flatMap(reservation -> ServerResponse
                        .ok()
                        .bodyValue(reservation)
                        .switchIfEmpty(ServerResponse.notFound().build()));
    }
}
