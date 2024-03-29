package payment;

import events.Saga.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import payment.data.Payment;
import payment.data.PaymentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
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
    public static final Sinks.Many<ClientNotificationEvent> sink_notify_client = Sinks.many().multicast().onBackpressureBuffer();

    @Bean
    public Function<Flux<ValidatePaymentEvent>, Mono<Void>> confirmReservationId() {
        return flux -> flux
                .doOnNext(event ->
                {
                    paymentService.findByPaymentId(event.getPayment_id())
                            .flatMap(payment ->  paymentService.updatePayment(payment.getPaymentId(),"reservationId", event.getReservation_id()))
                            .doOnNext(a -> System.out.println("Added reservation id to payment id: "+ a.getPaymentId()))
                            .log("Updating Reservation ID")
                            .subscribe();
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
                                UnblockResourcesEvent unblocking = new UnblockResourcesEvent(
                                        event.getPrice(),
                                        event.getUser_id(),
                                        event.getOfferId(),
                                        event.getFlight_id(),
                                        event.getPayment_id(),
                                        event.getReservation_id(),
                                        event.getSeatsNeeded()
                                );
                                unblocking.setExpiry(true);
                                sink_failed.tryEmitNext(unblocking);
                                sink_notify_client.tryEmitNext(new ClientNotificationEvent(
                                        event.getUser_id(),
                                        "Purchase window Expired",
                                        "unicast",
                                        new HashMap<>() {{
                                            put("offerId", event.getOfferId());
                                            put("flightId", event.getFlight_id());
                                        }}
                                ));
                            })
                            .log("Checking Payment")
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
                .doOnNext(a -> {
                    System.out.println("Refunding Payment"+ a.getPaymentId());
                    sink_notify_client.tryEmitNext(new ClientNotificationEvent(
                            a.getUserId(),
                            "Purchase Refunded!",
                            "unicast",
                            new HashMap<>() {{
                                put("offerId", a.getOfferId());
                                put("flightId", a.getFlightId());
                            }}
                    ));
                })
                .log("Refunding Payment")
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

    @Bean
    public Supplier<Flux<ClientNotificationEvent>> notifyClient() {
        return sink_notify_client::asFlux;
    }
}

