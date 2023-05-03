package payment.data;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    public Mono<Payment> findByPaymentId(String paymentId){
        return paymentRepository.findByPaymentId(paymentId).switchIfEmpty(Mono.empty());
    }

    public Mono<Payment> deleteByPaymentId(String paymentId){
        return paymentRepository.findByPaymentId(paymentId)
                .flatMap(existingPayment -> paymentRepository.delete(existingPayment)
                        .then(Mono.just(existingPayment)));
    }

    public Mono<Payment> updatePayment(String paymentId, String reservationId, Boolean isPaid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("paymentId").is(paymentId));

        Update update = new Update();
        if(reservationId != null) {
            update.set("reservationId", reservationId);
        }
        if(!isPaid) {
            update.set("isPaid", false);
        }

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Payment.class);

    }
}
