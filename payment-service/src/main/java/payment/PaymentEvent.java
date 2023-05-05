package payment;

import events.Saga.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class PaymentEvent {
    public static final Sinks.Many<MakeReservationEvent> sink_new_reservation = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<ValidatePaymentEvent> sink_validations = Sinks.many().multicast().onBackpressureBuffer();
    static final Sinks.Many<PayReservationEvent> sink_succeeded = Sinks.many().multicast().onBackpressureBuffer();

    @Bean
    public Function<Flux<RequirePaymentEvent>, Flux<ValidatePaymentEvent>> processPayment() {
        return flux -> flux
                .doOnNext(event -> System.out.println("paying for offer:" + event.getOfferId()))
                .map(event -> new ValidatePaymentEvent(event.getPrice(),"", event.getOfferId(),event.getFlightId(),event.getSeatsNeeded(), "4"));
    }

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
    public Function<Flux<ValidatePaymentEvent>, Mono<Void>> validatePayment() {
        return flux -> flux
                .doOnNext(PaymentEvent::verifyIfPaid)
                .then();
    }

    private static void verifyIfPaid(ValidatePaymentEvent event)
    {
        System.out.println("Checking Payment");
        boolean success = false;
        if(!success) {
            System.out.println("Payment failed. Rollback. ");
            //sink_failed.tryEmitNext(new UnblockResourcesEvent(event.getPrice(), "",event.getOfferId(), event.getFlightId(), event.getSeatsNeeded()));
        }
    }
//
//    @Bean
//    public Supplier<Flux<UnblockResourcesEvent>> failTransaction() {
//        return sink_failed::asFlux;
//    }

    @Bean
    public Supplier<Flux<PayReservationEvent>> succeedTransaction() {
        return sink_succeeded::asFlux;
    }
    @Bean
    public Supplier<Flux<MakeReservationEvent>> makeReservation() {
        return sink_new_reservation::asFlux;
    }
    @Bean
    public Supplier<Flux<ValidatePaymentEvent>> requireValidation() {
        return sink_validations::asFlux;
    }


}

