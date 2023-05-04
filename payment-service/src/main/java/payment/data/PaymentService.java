package payment.data;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class PaymentService {

    private ReactiveMongoTemplate reactiveMongoTemplate;
    private PaymentRepository paymentRepository;

    public PaymentService(ReactiveMongoTemplate reactiveMongoTemplate, PaymentRepository paymentRepository) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.paymentRepository = paymentRepository;
    }

    public Mono<Payment> createPayment(Payment payment){
        return paymentRepository.save(payment);
    }

    public Flux<Payment> getAllPayments(){
        return paymentRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Payment> findByPaymentId(String paymentID){
        return paymentRepository.findByPaymentId(paymentID).switchIfEmpty(Mono.empty());
    }

    public Mono<Payment> deleteByPaymentId(String paymentID){
        return paymentRepository.findByPaymentId(paymentID)
                .flatMap(existingPayment -> paymentRepository.delete(existingPayment)
                        .then(Mono.just(existingPayment)));
    }
    /*
    // updating the specific offer with the given parameters (null parameters - don't update the field)
    public Mono<Reservation> updateReservation(Reservation reservation){
        return reservationRepository.findById(reservation.getReservationID())
                .flatMap(dbReservations -> {
                    reservation.getUserID().ifPresent(dbReservations::setUserID);

                    reservation.getHotelID().ifPresent(dbReservations::setHotelID);

                    reservation.getFlightID().ifPresent(dbReservations::setFlightID);

                    reservation.getIsPaid().ifPresent(dbReservations::setIsPaid);

                    return reservationRepository.save(dbReservations);
                });
    }

*/
}
