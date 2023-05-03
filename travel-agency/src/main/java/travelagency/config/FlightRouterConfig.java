package travelagency.config;

import travelagency.handler.FlightWebLayerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class FlightRouterConfig {
    @Bean
    RouterFunction<ServerResponse> createFlight(FlightWebLayerHandler handler) {
        return route(POST("/travel_agency/flights"), handler::createFlight);
    }

    @Bean
    RouterFunction<ServerResponse> updateFlightById(FlightWebLayerHandler handler) {
        return route(PUT("/travel_agency/flights/{flightId}"), handler::updateFlightById);
    }

    @Bean
    RouterFunction<ServerResponse> deleteFlightById(FlightWebLayerHandler handler) {
        return route(DELETE("/travel_agency/flights/{flightId}"), handler::deleteFlightById);
    }
}
