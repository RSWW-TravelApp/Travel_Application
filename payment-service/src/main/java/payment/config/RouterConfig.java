package payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import payment.handler.PaymentWebLayerHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> createPayment(PaymentWebLayerHandler handler) {
        return route(POST("/pay"), handler::createPayment);
    }

    @Bean
    RouterFunction<ServerResponse> updatePayment(PaymentWebLayerHandler handler) {
        return route(PUT("/pay/{paymentId}"), handler::updatePayment);
    }

    @Bean
    RouterFunction<ServerResponse> createReservation(PaymentWebLayerHandler handler) {
        return route(POST("/makereservation"), handler::createReservation);
    }

}
