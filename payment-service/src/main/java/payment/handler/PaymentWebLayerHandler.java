package payment.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import payment.data.Payment;
import payment.data.PaymentService;
import reactor.core.publisher.Mono;

@Component
public class PaymentWebLayerHandler {

    private final PaymentService paymentService;

    public PaymentWebLayerHandler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public Mono<ServerResponse> createPayment(ServerRequest request) {
//        Mono<Payment> paymentMono = request.bodyToMono(Payment.class);
//
//        return paymentMono
//                .flatMap(paymentObject -> ServerResponse
//                        .status(HttpStatus.CREATED)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(paymentService.createPayment(paymentObject), Payment.class)
//                );
        return null;
    }

    public Mono<ServerResponse> updatePayment(ServerRequest request) {
//        Mono<Payment> paymentMono = request.bodyToMono(Payment.class);
//        String id = request.pathVariable("paymentId");
//
//        return paymentMono
//                .flatMap(paymentObject -> ServerResponse
//                        .status(HttpStatus.CREATED)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(paymentService.updatePayment(paymentObject, id), Payment.class)
//                );
        return null;
    }

    public Mono<ServerResponse> createReservation(ServerRequest request) {
//        Mono<Payment> paymentMono = request.bodyToMono(Payment.class);
//
//        return paymentMono
//                .flatMap(paymentObject -> ServerResponse
//                        .status(HttpStatus.CREATED)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(paymentService.createReservation(paymentObject), Payment.class)
//                );
        return null;
    }











}
