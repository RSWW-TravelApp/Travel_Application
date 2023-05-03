package travelagency.config;

import travelagency.handler.OfferWebLayerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class OfferRouterConfig {
    @Bean
    RouterFunction<ServerResponse> createOffer(OfferWebLayerHandler handler) {
        return route(POST("/travel_agency/offers"), handler::createOffer);
    }

    @Bean
    RouterFunction<ServerResponse> updateOfferById(OfferWebLayerHandler handler) {
        return route(PUT("/travel_agency/offers/{offerId}"), handler::updateOfferById);
    }

    @Bean
    RouterFunction<ServerResponse> deleteOfferById(OfferWebLayerHandler handler) {
        return route(DELETE("/travel_agency/offers/{offerId}"), handler::deleteOfferById);
    }





}
