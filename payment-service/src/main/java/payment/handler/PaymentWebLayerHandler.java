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

    public Mono<ServerResponse> createPaidPayment(ServerRequest request) {
//        Mono<Payment> paymentMono = request.bodyToMono(Payment.class);
//
//        return paymentMono
//                .flatMap(paymentObject -> ServerResponse
//                        .status(HttpStatus.CREATED)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(paymentService.createPayment(paymentObject), Payment.class)
//                );
        return Mono.empty();
    }

    public Mono<ServerResponse> updatePaymentToPaid(ServerRequest request) {
//        Mono<Payment> paymentMono = request.bodyToMono(Payment.class);
//        String id = request.pathVariable("paymentId");
//
//        return paymentMono
//                .flatMap(paymentObject -> ServerResponse
//                        .status(HttpStatus.CREATED)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(paymentService.updatePayment(paymentObject, id), Payment.class)
//                );
        return Mono.empty();
    }

    public Mono<ServerResponse> createUnpaidPayment(ServerRequest request) {
//        Mono<Payment> paymentMono = request.bodyToMono(Payment.class);
//
//        return paymentMono
//                .flatMap(paymentObject -> ServerResponse
//                        .status(HttpStatus.CREATED)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(paymentService.createUnpaidPayment(paymentObject), Payment.class)
//                );
        return Mono.empty();
    }











}
