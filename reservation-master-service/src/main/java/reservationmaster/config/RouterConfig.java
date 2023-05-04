package reservationmaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reservationmaster.handler.ReservationMasterWebLayerHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> createReservation(ReservationMasterWebLayerHandler handler) {
        return route(POST("/reservations"), handler::createReservation);
    }

    @Bean
    RouterFunction<ServerResponse> addEvent(ReservationMasterWebLayerHandler handler) {
        return route(PUT("/reservations/{reservationId}"), handler::addEvent);
    }

    @Bean
    RouterFunction<ServerResponse> deleteReservationById(ReservationMasterWebLayerHandler handler) {
        return route(DELETE("/reservations/{reservationId}"), handler::deleteReservationById);
    }
}
