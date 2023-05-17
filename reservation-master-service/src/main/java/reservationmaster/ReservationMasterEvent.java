package reservationmaster;

import events.CQRS.reservations.CreateReservationEvent;
import events.CQRS.reservations.DeleteReservationEvent;
import events.CQRS.reservations.UpdateReservationEvent;
import events.Saga.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reservationmaster.data.Reservation;
import reservationmaster.data.ReservationMasterService;
import reservationmaster.data.ReservationNested;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Map;


@Component
public class ReservationMasterEvent {

    private final ReservationMasterService reservationMasterService;
    public static final Sinks.Many<ValidatePaymentEvent> sink_validation = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<TONotificationEvent> sink_successful_reservations = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<ConfirmReservationIdEvent> sink_payment_updates = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<RefundPaymentEvent> sink_refunds = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<CreateReservationEvent> sink_CQRS_create = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<DeleteReservationEvent> sink_CQRS_delete = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<UpdateReservationEvent> sink_CQRS_update = Sinks.many().multicast().onBackpressureBuffer();
    public static final Sinks.Many<ClientNotificationEvent> sink_notify_client = Sinks.many().multicast().onBackpressureBuffer();
    public ReservationMasterEvent(ReservationMasterService reservationMasterService) {
        this.reservationMasterService = reservationMasterService;
    }



    @Bean
    public Function<Flux<MakeReservationEvent>, Flux<BlockResourcesEvent>> makeReservation() {

        return flux -> flux
                .flatMap(event ->
                        reservationMasterService.createReservation(new Reservation(null,event.getUser_id(),event.getOfferId(), event.getFlight_id(), event.getPayment_id(), Boolean.parseBoolean(event.getIs_paid()),false,event.getSeatsNeeded(), event.getPrice(),false))
                )
                .map(event -> new BlockResourcesEvent(event.getPrice(),event.getUserId(),event.getOfferId(), event.getFlightId(), event.getPaymentId(),event.getReservationId(), event.getTravellers()))
                .doOnNext(event -> System.out.println("Created reservation with ID" + event.getReservation_id()));
    }

    @Bean
    public Function<Flux<RequirePaymentEvent>, Mono<Void>> confirmReservation()
    {
        return flux -> flux
                .doOnNext(event -> System.out.println("Received confirmation that resources have been blocked for: "+ event.getReservation_id()))
                .flatMap(event -> reservationMasterService.addEvent(new ReservationNested(event.getReservation_id(),null, null, null,null,null,null, null,null,true,"ReserveReservation")))
                .doOnNext(event ->
                    event.setReserved(true))
                .filter(Reservation::getIsPaid)
                .doOnDiscard(Reservation.class , event ->
                        {
                            System.out.println("Still have to wait for payment for "+ event.getReservationId());
                            sink_payment_updates.tryEmitNext(new ConfirmReservationIdEvent(event.getPrice(),event.getUserId(),event.getOfferId(),event.getFlightId(),event.getPaymentId(),event.getReservationId(),event.getTravellers()));
                            sink_validation.tryEmitNext(new ValidatePaymentEvent(event.getPrice(),event.getUserId(),event.getOfferId(),event.getFlightId(),event.getPaymentId(),event.getReservationId(),event.getTravellers()));
                            sink_notify_client.tryEmitNext(new ClientNotificationEvent(
                                    event.getUserId(),
                                    "Reservation successful",
                                    new HashMap<String, String>() {{
                                        put("reservationId", event.getReservationId());
                                    }}
                            ));
                        }
                )
                .flatMap(event -> reservationMasterService.addEvent(new ReservationNested(event.getReservationId(),null,null,null,null,null,null,null,null,null,"CompleteReservation")))
                .doOnNext(event ->
                    {
                        System.out.println("Time to tell TO "+ event.getReservationId());
                        sink_payment_updates.tryEmitNext(new ConfirmReservationIdEvent(event.getPrice(),event.getUserId(),event.getOfferId(),event.getFlightId(),event.getPaymentId(),event.getReservationId(),event.getTravellers()));
                        sink_successful_reservations.tryEmitNext(new TONotificationEvent(event.getPrice(),event.getUserId(),event.getOfferId(),event.getFlightId(),event.getPaymentId(),event.getReservationId(),event.getTravellers()));

                })
                .then();
    }

    @Bean
    public Function<Flux<RemoveReservationEvent>, Mono<Void>> removeReservation() {
        return flux -> flux
                .flatMap(event -> reservationMasterService.addEvent(new ReservationNested(event.getReservation_id(),null, null, null,null,null,null, true,null,null,"CancelReservation")))
                .doOnNext(event -> {
                    System.out.println("Cancelling reservation:" + event.getReservationId());
                    event.setReserved(false);
                    sink_notify_client.tryEmitNext(new ClientNotificationEvent(
                            event.getUserId(),
                            "Reservation cancelled",
                            new HashMap<String, String>() {{
                                put("reservationId", event.getReservationId());
                            }}
                    ));
                })
                .doOnNext(event ->{
                    sink_refunds.tryEmitNext(new RefundPaymentEvent(event.getPrice(),event.getUserId(),event.getOfferId(),event.getFlightId(),event.getPaymentId(),event.getReservationId(),event.getTravellers()));
                })
                .then();
    }

    @Bean
    public Function<Flux<PayReservationEvent>, Mono<Void>> receivePayment() {
        return flux -> flux
                .flatMap(event -> reservationMasterService.addEvent(new ReservationNested(event.getReservation_id(),null, null, null,null,null,true, null,null,null,"PayReservation")))
                .doOnNext(event -> System.out.println("Marking reservation as paid :" + event.getReservationId()))
                .filter(Reservation::getReserved)
                .flatMap(event -> reservationMasterService.addEvent(new ReservationNested(event.getReservationId(),null,null,null,null,null,null,null,null,null,"CompleteReservation")))
                .doOnNext(event -> {
                    System.out.println("Time to tell TO "+ event.getReservationId());
                    sink_successful_reservations.tryEmitNext(new TONotificationEvent(event.getPrice(), event.getUserId(), event.getOfferId(), event.getFlightId(), event.getPaymentId(), event.getReservationId(), event.getTravellers()));
                })
                .then();
    }

    @Bean
    public Supplier<Flux<ConfirmReservationIdEvent>> confirmReservationId() {
        return sink_payment_updates::asFlux;
    }
    @Bean
    public Supplier<Flux<ValidatePaymentEvent>> requireValidation() {
        return sink_validation::asFlux;
    }

    @Bean
    public Supplier<Flux<TONotificationEvent>> finaliseReservation() {
        return sink_successful_reservations::asFlux;
    }

    @Bean
    public Supplier<Flux<RefundPaymentEvent>> requestRefund() {
        return sink_refunds::asFlux;
    }

    @Bean
    public Supplier<Flux<CreateReservationEvent>> createReservationCQRSHandle() {
        return sink_CQRS_create::asFlux;
    }
    @Bean
    public Supplier<Flux<DeleteReservationEvent>> deleteReservationCQRSHandle() {
        return sink_CQRS_delete::asFlux;
    }
    @Bean
    public Supplier<Flux<UpdateReservationEvent>> updateReservationCQRSHandle() {
        return sink_CQRS_update::asFlux;
    }
    @Bean
    public Supplier<Flux<ClientNotificationEvent>> notifyClient() {
        return sink_notify_client::asFlux;
    }
}
