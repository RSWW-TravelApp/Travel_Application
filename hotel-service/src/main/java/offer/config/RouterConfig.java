package offer.config;

import offer.handler.OfferWebLayerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> getOffers(OfferWebLayerHandler handler) {
        return route(GET("/offers"), handler::getOffers);
    }

    @Bean
    RouterFunction<ServerResponse> createOffer(OfferWebLayerHandler handler) {
        return route(POST("/offers"), handler::createOffer);
    }

    @Bean
    RouterFunction<ServerResponse> getOfferById(OfferWebLayerHandler handler) {
        return route(GET("/offers/{offerId}"), handler::getOfferById);
    }

    @Bean
    RouterFunction<ServerResponse> getStatsOffers(OfferWebLayerHandler handler) {
        return route(GET("/stats/offers"), handler::getStats);
    }

    @Bean
    RouterFunction<ServerResponse> updateOfferById(OfferWebLayerHandler handler) {
        return route(PUT("/offers/{offerId}"), handler::updateOfferById);
    }

    @Bean
    RouterFunction<ServerResponse> deleteOfferById(OfferWebLayerHandler handler) {
        return route(DELETE("/offers/{offerId}"), handler::deleteOfferById);
    }





}
