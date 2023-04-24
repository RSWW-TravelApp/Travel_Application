package flight.config;

import flight.handler.FlightWebLayerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    RouterFunction<ServerResponse> getFlights(FlightWebLayerHandler handler) {
        return route(GET("/flights"), handler::getAllFlights);
    }

    @Bean
    RouterFunction<ServerResponse> createFlight(FlightWebLayerHandler handler) {
        return route(POST("/flights"), handler::createFlight);
    }

    @Bean
    RouterFunction<ServerResponse> getFlightById(FlightWebLayerHandler handler) {
        return route(GET("/flights/{flightId}"), handler::getFlightById);
    }

    @Bean
    RouterFunction<ServerResponse> updateFlightById(FlightWebLayerHandler handler) {
        return route(PUT("/flights/{flightId}"), handler::updateFlightById);
    }

    @Bean
    RouterFunction<ServerResponse> deleteFlightById(FlightWebLayerHandler handler) {
        return route(DELETE("/flights/{flightId}"), handler::deleteFlightById);
    }
}
