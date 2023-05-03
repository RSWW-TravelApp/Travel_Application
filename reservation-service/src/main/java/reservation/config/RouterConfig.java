package reservation.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reservation.handler.ReservationWebLayerHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> getReservations(ReservationWebLayerHandler handler) {
        return route(GET("/reservations"), handler::getReservations);
    }

    @Bean
    RouterFunction<ServerResponse> getReservationById(ReservationWebLayerHandler handler) {
        return route(GET("/reservations/{reservationId}"), handler::getReservationById);
    }


}
