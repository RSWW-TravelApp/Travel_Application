package payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import payment.handler.PaymentWebLayerHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> getPayments(PaymentWebLayerHandler handler) {
        return route(GET("/payments"), handler::getPayments);
    }

    @Bean
    RouterFunction<ServerResponse> getPaymentById(PaymentWebLayerHandler handler) {
        return route(GET("/payments/{paymentId}"), handler::getPaymentById);
    }
}