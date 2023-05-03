package travelagency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import travelagency.handler.TravelAgencyWebLayerHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> createOffer(TravelAgencyWebLayerHandler handler) {
        return route(POST("/offers"), handler::createOffer);
    }

    @Bean
    RouterFunction<ServerResponse> addEventOffer(TravelAgencyWebLayerHandler handler) {
        return route(PUT("/offers/{offerId}"), handler::addEventOffer);
    }

    @Bean
    RouterFunction<ServerResponse> deleteOfferById(TravelAgencyWebLayerHandler handler) {
        return route(DELETE("/offers/{offerId}"), handler::deleteOfferById);
    }

    @Bean
    RouterFunction<ServerResponse> createFlight(TravelAgencyWebLayerHandler handler) {
        return route(POST("/flights"), handler::createFlight);
    }

    @Bean
    RouterFunction<ServerResponse> addEventFlight(TravelAgencyWebLayerHandler handler) {
        return route(PUT("/flights/{flightId}"), handler::addEventFlight);
    }

    @Bean
    RouterFunction<ServerResponse> deleteFlightById(TravelAgencyWebLayerHandler handler) {
        return route(DELETE("/flights/{flightId}"), handler::deleteFlightById);
    }
}
