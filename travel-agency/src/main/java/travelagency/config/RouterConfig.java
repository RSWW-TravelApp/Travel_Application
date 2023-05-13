package travelagency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import travelagency.handler.TravelAgencyWebLayerHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    RouterFunction<ServerResponse> createOffer(TravelAgencyWebLayerHandler handler) {
        return route(POST("/offers"), handler::createOffer);
    }

    @Bean
    RouterFunction<ServerResponse> createFlight(TravelAgencyWebLayerHandler handler) {
        return route(POST("/flights"), handler::createFlight);
    }

}
