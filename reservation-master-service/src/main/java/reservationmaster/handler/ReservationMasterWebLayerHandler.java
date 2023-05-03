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

    public Mono<ServerResponse> updateReservationById(ServerRequest request) {
        String id = request.pathVariable("reservationId");
        Mono<Reservation> updatedReservation = request.bodyToMono(Reservation.class);

        return updatedReservation
                .flatMap(reservation -> {
                    String userId = reservation.getUserId().orElse(null);
                    String flightId = reservation.getFlightId().orElse(null);
                    String offerId = reservation.getOfferId().orElse(null);
                    Boolean isPaid = reservation.getIsPaid().orElse(null);
                    List<ReservationNested> events = reservation.getEvents();

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(
                                    reservationMasterService.updateReservation(id, userId, flightId, offerId, isPaid,
                                            events),
                                    Reservation.class
                            );
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> addEvent(ServerRequest request) {
        String id = request.pathVariable("reservationId");

        Mono<ReservationNested> updatedReservation = request.bodyToMono(ReservationNested.class);

        return updatedReservation
                .flatMap(reservationNested -> {
                    String userId = reservationNested.getUserId().orElse(null);
                    String flightId = reservationNested.getFlightId().orElse(null);
                    String offerId = reservationNested.getOfferId().orElse(null);
                    Boolean isPaid = reservationNested.getIsPaid().orElse(null);
                    String eventType = reservationNested.getEventType().orElse(null);

                    ReservationNested reservation1 = new ReservationNested(id, userId, flightId, offerId, isPaid,
                            eventType);

                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(
                                    reservationMasterService.addEvent(reservation1),
                                    ReservationNested.class
                            );
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteReservationById(ServerRequest request){
        return Mono.from(reservationMasterService.deleteReservationById(request.pathVariable("reservationId")))
                .flatMap(reservation -> ServerResponse
                        .ok()
                        .bodyValue(reservation))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

}

