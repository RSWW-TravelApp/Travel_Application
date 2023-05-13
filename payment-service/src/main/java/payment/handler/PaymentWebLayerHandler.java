package payment.handler;

import events.Saga.MakeReservationEvent;
import events.Saga.PayReservationEvent;
import events.Saga.ValidatePaymentEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import payment.PaymentEvent;
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
                .body(paymentService.getPayments(), Payment.class);
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

    public Mono<ServerResponse> createUnpaidPayment(ServerRequest request) {
        Mono<Payment> paymentMono = request.bodyToMono(Payment.class);
        return paymentMono
                .flatMap(paymentService::createUnpaidPayment)
                .doOnNext(payment ->
                    {
                        System.out.println("Created Unpaid Payment Entry: " + payment.getPaymentId());
                        payment.setIsPaid(false);

                        PaymentEvent.sink_new_reservation.tryEmitNext(
                            new MakeReservationEvent(
                                    payment.getPrice(),
                                    payment.getOfferId(),
                                    payment.getFlightId(),
                                    payment.getSeatsNeeded(),
                                    payment.getUserId(),
                                    payment.getPaymentId(),
                                    null,
                                    payment.getIsPaid().toString()));
                    })
                .flatMap(paymentObject -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(paymentObject)
                );
    }

    public Mono<ServerResponse> createPaidPayment(ServerRequest request) {
        Mono<Payment> paymentMono = request.bodyToMono(Payment.class);
        String status = request.pathVariable("status");

        return paymentMono
                .flatMap(paymentObject -> {
                    if (paymentObject == null) {
                        return ServerResponse
                                .status(HttpStatus.NOT_FOUND)
                                .body(Mono.just("404,Not found"), String.class);
                    }
                    switch (status) {
                        case "success":
                            return paymentService.createPaidPayment(paymentObject, true)
                                    .doOnNext(payment ->
                                    {
                                        payment.setIsPaid(true);
                                        PaymentEvent.sink_new_reservation.tryEmitNext(
                                                new MakeReservationEvent(
                                                        payment.getPrice(),
                                                        payment.getOfferId(),
                                                        payment.getFlightId(),
                                                        payment.getSeatsNeeded(),
                                                        payment.getUserId(),
                                                        payment.getPaymentId(),
                                                        null,
                                                        payment.getIsPaid().toString()));
//                                        PaymentEvent.sink_validation.tryEmitNext(
//                                                new ValidatePaymentEvent(
//                                                        payment.getPrice(),
//                                                        payment.getUserId(),
//                                                        payment.getOfferId(),
//                                                        payment.getFlightId(),
//                                                        payment.getSeatsNeeded(),
//                                                        payment.getPaymentId()
//                                                )
//                                        );
                                    })
                                    .doOnNext(a -> System.out.println("Created Paid Payment Entry: " + a.getPaymentId()+a.getIsPaid()))
                                    .flatMap(updatedPayment ->
                                            ServerResponse.status(HttpStatus.OK)
                                                    .body(Mono.just("200,Entry created, Payment succeeded"), String.class)
                                    );
                        case "fail":
                            return paymentService.createPaidPayment(paymentObject, false)
                                    .doOnNext(payment ->
                                    {
                                        payment.setIsPaid(false);
                                        PaymentEvent.sink_new_reservation.tryEmitNext(
                                                new MakeReservationEvent(
                                                        payment.getPrice(),
                                                        payment.getOfferId(),
                                                        payment.getFlightId(),
                                                        payment.getSeatsNeeded(),
                                                        payment.getUserId(),
                                                        payment.getPaymentId(),
                                                        null,
                                                        payment.getIsPaid().toString()));
                                    })
                                    .flatMap(updatedPayment ->
                                            ServerResponse.status(HttpStatus.PAYMENT_REQUIRED)
                                                    .body(Mono.just("402, Entry created, Payment failed"), String.class)
                                    );
                        case "random":
                            Random rand = new Random();
                            if (rand.nextInt(0, 100) < 15) {
                                return paymentService.createPaidPayment(paymentObject, false)
                                        .doOnNext(payment ->
                                        {
                                            payment.setIsPaid(false);
                                            PaymentEvent.sink_new_reservation.tryEmitNext(
                                                    new MakeReservationEvent(
                                                            payment.getPrice(),
                                                            payment.getOfferId(),
                                                            payment.getFlightId(),
                                                            payment.getSeatsNeeded(),
                                                            payment.getUserId(),
                                                            payment.getPaymentId(),
                                                            null,
                                                            payment.getIsPaid().toString()));
                                        })
                                        .doOnNext(a -> System.out.println("Created Paid Payment Entry: " + a.getPaymentId()))
                                        .flatMap(updatedPayment ->
                                                ServerResponse.status(HttpStatus.PAYMENT_REQUIRED)
                                                        .body(Mono.just("402, Entry created, Payment failed"), String.class)
                                        );
                            }
                            // SAGA
                            return paymentService.createPaidPayment(paymentObject, true)
                                    .doOnNext(payment ->
                                    {
                                        payment.setIsPaid(true);
                                        PaymentEvent.sink_new_reservation.tryEmitNext(
                                                new MakeReservationEvent(
                                                        payment.getPrice(),
                                                        payment.getOfferId(),
                                                        payment.getFlightId(),
                                                        payment.getSeatsNeeded(),
                                                        payment.getUserId(),
                                                        payment.getPaymentId(),
                                                        null,
                                                        payment.getIsPaid().toString()));
                                    })
                                    .flatMap(updatedPayment ->
                                            ServerResponse.status(HttpStatus.OK)
                                                    .body(Mono.just("200, Entry created, Payment is processed"), String.class)
                                    );
                        default:
                            return ServerResponse
                                    .badRequest()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue("Status must be one of the following: ['fail', 'success', 'random']");
                    }
                });
    }

    public Mono<ServerResponse> updatePayment(ServerRequest request) {
        String paymentId = request.pathVariable("paymentId");
        String status = request.pathVariable("status");

        return paymentService.findByPaymentId(paymentId)
                .hasElement()
                .flatMap(exist -> {
                    if (!exist) {
                        return ServerResponse
                                .status(HttpStatus.NOT_FOUND)
                                .body(Mono.just("404,Not found"), String.class);
                    }
                    switch (status) {
                        case "fail":
                            // 100% for fail
                            return ServerResponse.status(HttpStatus.PAYMENT_REQUIRED)
                                                    .body(Mono.just("402,Payment cannot be processed"), String.class);

                        case "success":
                            // ~0% for fail BUT SAGA can fail, so it is not exactly 0%
                            return
                                    paymentService.findByPaymentId(paymentId)
                                    .filter(a -> !a.getIsPaid() && a.getReservationId() != null)
                                    .flatMap(a -> paymentService.updatePayment(paymentId, "isPaid", true))
                                    .doOnNext(payment -> {
                                        payment.setIsPaid(true);
                                        System.out.println("Paid for payment:" + payment.getPaymentId());
                                        PaymentEvent.sink_succeeded.tryEmitNext(
                                                new PayReservationEvent(
                                                        payment.getPrice(),
                                                        payment.getUserId(),
                                                        payment.getOfferId(),
                                                        payment.getFlightId(),
                                                        payment.getPaymentId(),
                                                        payment.getReservationId(),
                                                        payment.getSeatsNeeded()));
                                    })
                                    .flatMap(updatedPayment ->
                                            ServerResponse.status(HttpStatus.OK)
                                                    .body(Mono.just("200,Payment is processed"), String.class)
                                    );
                        case "random":
                            // 15% for fail
                            Random rand = new Random();
                            if (rand.nextInt(0, 100) < 15) {
                                //return paymentService.updatePayment(paymentId, "isPaid", false)
                                return ServerResponse.status(HttpStatus.PAYMENT_REQUIRED)
                                                        .body(Mono.just("402,Payment cannot be processed"), String.class);

                            }
                            // SAGA
                            return
                                    paymentService.findByPaymentId(paymentId)
                                    .filter(a -> !a.getIsPaid() && a.getReservationId() != null)
                                    .flatMap(a -> paymentService.updatePayment(paymentId, "isPaid", true))
                                    .doOnNext(payment -> {
                                        payment.setIsPaid(true);
                                        System.out.println("Paid for payment:" + payment.getPaymentId());
                                        PaymentEvent.sink_succeeded.tryEmitNext(
                                                new PayReservationEvent(
                                                        payment.getPrice(),
                                                        payment.getUserId(),
                                                        payment.getOfferId(),
                                                        payment.getFlightId(),
                                                        payment.getPaymentId(),
                                                        payment.getReservationId(),
                                                        payment.getSeatsNeeded()));
                                    })
                                    .flatMap(updatedPayment ->
                                            ServerResponse.status(HttpStatus.OK)
                                                    .body(Mono.just("200,Payment is processed"), String.class)
                                    );
                        default:
                            return ServerResponse
                                    .status(HttpStatus.BAD_REQUEST)
                                    .body(Mono.just("400,Status must be one of the following ['fail', 'success', 'random']"), String.class);
                    }
                });
    }


}

