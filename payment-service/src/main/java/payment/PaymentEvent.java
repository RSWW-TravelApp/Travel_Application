package payment;

import events.Saga.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import payment.data.Payment;
import payment.data.PaymentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class PaymentEvent {
    private final PaymentService paymentService;

    public PaymentEvent(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public static final Sinks.Many<MakeReservationEvent> sink_new_reservation = Sinks.many().multicast().onBackpressureBuffer();

    public static final Sinks.Many<PayReservationEvent> sink_succeeded = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<UnblockResourcesEvent> sink_failed = Sinks.many().multicast().onBackpressureBuffer();
//    public static final Sinks.Many<RemoveReservationEvent> sink_failed_unreserved = Sinks.many().multicast().onBackpressureBuffer();

//    @Bean
//    public Function<Flux<RequirePaymentEvent>, Flux<ValidatePaymentEvent>> processPayment() {
//        return flux -> flux
//                .doOnNext(event -> System.out.println("paying for offer:" + event.getOfferId()))
//                .map(event -> new ValidatePaymentEvent(event.getPrice(),"", event.getOfferId(),event.getFlightId(),event.getSeatsNeeded(), "4"));
//    }

//    @GetMapping("/pay/")
//    public void payReservation() {
//        System.out.println("payingForReservation");
//        sink_succeeded.tryEmitNext(new PayReservationEvent(455.4,"f","g","4",4));
//
//    }

//    @Bean
//    public Function<Flux<ValidatePaymentEvent>, Flux<ValidatePaymentEvent>> pp() {
//        return flux -> flux
//                .doOnNext(event -> System.out.println("paying for offer2222:" + event.getOfferId()));
//        //.map(event -> new ValidatePaymentEvent(event.getPrice(),event.getOfferId(),event.getFlightId(),event.getSeatsNeeded(), "payment_id"));
//    }

    //    @Bean
//    public Function<Flux<RequirePaymentEvent>, Flux<MakeReservationEvent>> processPayment() {
//        return flux -> flux
//                .doOnNext(event -> System.out.println("blocking offer:" + event.getOfferId()))
//                .map(event -> new MakeReservationEvent(123.0,"123", "123456", 59,"Big",3,0,0,0,"yes please", 5, "2020-04-04", "2020-04-09", "false", "false"));
//    }
    @Bean
    public Function<Flux<ValidatePaymentEvent>, Mono<Void>> confirmReservationId() {
        return flux -> flux
                .doOnNext(event ->
                {
                    paymentService.findByPaymentId(event.getPayment_id())
                            .flatMap(payment ->  paymentService.updatePayment(payment.getPaymentId(),"reservationId", event.getReservation_id()))
                            .subscribe();
                           //.subscribe();
                })
                .then();
    }

    @Bean
    public Function<Flux<ValidatePaymentEvent>, Mono<Void>> validatePayment() {
        return flux -> flux
                .doOnNext(event ->
                {
                    paymentService.findByPaymentId(event.getPayment_id())
                            .filter(a -> !a.getIsPaid())
                            .flatMap(payment ->  paymentService.updatePayment(payment.getPaymentId(),"isExpired",true))
                            .doOnNext(payment ->
                            {
                                System.out.println("Payment failed. Rollback. ");
                                sink_failed.tryEmitNext(new UnblockResourcesEvent(
                                        event.getPrice(),
                                        event.getUser_id(),
                                        event.getOfferId(),
                                        event.getFlight_id(),
                                        event.getPayment_id(),
                                        event.getReservation_id(),
                                        event.getSeatsNeeded()
                                ));
                            })
                            .subscribe();
                }).then();
    }

    @Bean
    public Function<Flux<RefundPaymentEvent>, Mono<Void>> refundPayment() {
        return flux -> flux
                .flatMap(event ->
                        paymentService.findByPaymentId(event.getPayment_id()))
                        .filter(a -> !a.getIsExpired())
                        .flatMap(payment ->  paymentService.updatePayment(payment.getPaymentId(),"isExpired",true))
                .filter(Payment::getIsPaid)
                .doOnNext(a -> System.out.println("Refunding Payment"+ a.getPaymentId()))
                .then();
    }

//    private static void verifyIfPaid(ValidatePaymentEvent event)
//    {
//        System.out.println("Checking Payment");
//        boolean success = false;
//        if(!success) {
//            System.out.println("Payment failed. Rollback. ");
//            //sink_failed.tryEmitNext(new UnblockResourcesEvent(event.getPrice(), "",event.getOfferId(), event.getFlightId(), event.getSeatsNeeded()));
//        }
//    }

    @Bean
    public Supplier<Flux<UnblockResourcesEvent>> failTransaction() {
        return sink_failed::asFlux;
    }
//    @Bean
//    public Supplier<Flux<RemoveReservationEvent>> failUnreservedTransaction() {
//        return sink_failed_unreserved::asFlux;
//    }
    @Bean
    public Supplier<Flux<PayReservationEvent>> succeedTransaction() {
        return sink_succeeded::asFlux;
    }
    @Bean
    public Supplier<Flux<MakeReservationEvent>> makeReservation() {
        return sink_new_reservation::asFlux;
    }



}

