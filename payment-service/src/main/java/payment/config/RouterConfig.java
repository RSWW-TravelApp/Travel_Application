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
    /*
    @Bean
    RouterFunction<ServerResponse> getPayments(PaymentWebLayerHandler handler) {
        return route(GET("/pay"), handler::getPayments);
    }
     */

    @Bean
    RouterFunction<ServerResponse> getPaymentById(PaymentWebLayerHandler handler) {
        return route(GET("/pay/{paymentId}"), handler::getPaymentById);
    }

    @Bean
    RouterFunction<ServerResponse> createPaidPayment(PaymentWebLayerHandler handler) {
        return route(POST("/pay/{status}"), handler::createPaidPayment);
    }

    @Bean
    RouterFunction<ServerResponse> updatePayment(PaymentWebLayerHandler handler) {
        return route(PUT("/pay/{paymentId}/{status}"), handler::updatePayment);
    }

    @Bean
    RouterFunction<ServerResponse> createUnpaidPayment(PaymentWebLayerHandler handler) {
        return route(POST("/makereservation"), handler::createUnpaidPayment);
    }

}
