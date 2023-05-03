package payment.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import payment.data.Payment;
import payment.data.PaymentService;
import reactor.core.publisher.Mono;

import java.util.Random;

@Component
public class PaymentWebLayerHandler {

    private final PaymentService paymentService;

    public PaymentWebLayerHandler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public Mono<ServerResponse> getPayments(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentService.getAllPayments(), Payment.class);
    }

    public Mono<ServerResponse> getPaymentById(ServerRequest request) {
        return paymentService
                .findByPaymentId(request.pathVariable("paymentId"))
                .flatMap(payment -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(payment), Payment.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> payForReservation(ServerRequest request) {
        String paymentId = request.pathVariable("paymentId");
        String status = request.pathVariable("status");

        /*Mono<ServerResponse> response = paymentService.findByPaymentId(request.pathVariable("paymentId"))
                .hasElement()
                .flatMap(exist -> {
                    if (!exist) {
                        return ServerResponse
                                .status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("Not found", String.class);
                    }
                    return null;
                });    HOW THE FUCK TO CHECK IF ELEMENT EXISTS IN DATABASE AAAAAAA */


        switch (status) {
            case "fail" -> {
                // 100% for fail
                return ServerResponse
                        .status(HttpStatus.PAYMENT_REQUIRED)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(Mono.just("Payment cannot be processed"), String.class);
            }
            case "success" -> {
                // ~0% for fail BUT SAGA can fail, so it is not exactly 0%

                return ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(Mono.just("Payment is processed"), String.class);
            }
            case "random" -> {
                // 15% for fail
                Random rand = new Random();
                if (rand.nextInt(0, 100) < 15) {
                    return ServerResponse
                            .status(HttpStatus.PAYMENT_REQUIRED)
                            .contentType(MediaType.TEXT_PLAIN)
                            .body(Mono.just("Payment cannot be processed"), String.class);
                }
                ;

                // SAGA

                return ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(Mono.just("Payment is processed"), String.class);
            }
        }

        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just("Status must be one of the following ['fail', 'success', 'random']"), String.class);
    }

}

