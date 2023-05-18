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


    @Bean
    public Supplier<Flux<UnblockResourcesEvent>> failTransaction() {
        return sink_failed::asFlux;
    }

    @Bean
    public Supplier<Flux<PayReservationEvent>> succeedTransaction() {
        return sink_succeeded::asFlux;
    }
    @Bean
    public Supplier<Flux<MakeReservationEvent>> makeReservation() {
        return sink_new_reservation::asFlux;
    }



}

