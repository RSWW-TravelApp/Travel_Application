package reservation.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reservation.handler.ReservationWebLayerHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> getReservations(ReservationWebLayerHandler handler) {
        return route(GET("/reservations"), handler::getReservations);
    }

    @Bean
    RouterFunction<ServerResponse> createReservation(ReservationWebLayerHandler handler) {
        return route(POST("/reservations"), handler::createReservation);
    }

    @Bean
    RouterFunction<ServerResponse> getReservationById(ReservationWebLayerHandler handler) {
        return route(GET("/reservations/{reservationId}"), handler::getReservationById);
    }

    @Bean
    RouterFunction<ServerResponse> updateReservationById(ReservationWebLayerHandler handler) {
        return route(PUT("/reservations/{reservationId}"), handler::updateReservationById);
    }

    @Bean
    RouterFunction<ServerResponse> deleteReservationById(ReservationWebLayerHandler handler) {
        return route(DELETE("/reservations/{reservationId}"), handler::deleteReservationById);
    }

}
