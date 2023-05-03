package reservationmaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reservationmaster.handler.ReservationMasterWebLayerHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    RouterFunction<ServerResponse> createReservation(ReservationMasterWebLayerHandler handler) {
        return route(POST("/reservation"), handler::makeReservation);
    }

    @Bean
    RouterFunction<ServerResponse> updateReservationById(ReservationMasterWebLayerHandler handler) {
        return route(PUT("/reservation/{reservationId}"), handler::updateReservation);
    }

    @Bean
    RouterFunction<ServerResponse> deleteReservationById(ReservationMasterWebLayerHandler handler) {
        return route(DELETE("/reservation/{reservationId}"), handler::deleteReservation);
    }
}
