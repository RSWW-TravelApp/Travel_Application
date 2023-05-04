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

    public Flux<Payment> getPayments(){
        return paymentRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Payment> findByPaymentId(String paymentId){
        return paymentRepository.findByPaymentId(paymentId).switchIfEmpty(Mono.empty());
    }

    public Mono<Payment> createUnpaidPayment(Payment payment){
        return paymentRepository.save(payment)
                .flatMap(savedPayment -> {
                    Query query = new Query();
                    query.addCriteria(Criteria.where("paymentId").is(savedPayment.getPaymentId()));

                    Update update = new Update();
                    update.set("isPaid", false);

                    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
                    return reactiveMongoTemplate.findAndModify(query, update, options, Payment.class);
                });
    }

    public Mono<Payment> createPaidPayment(Payment payment, boolean isPaid) {
        return paymentRepository.save(payment)
                .flatMap(savedPayment -> {
                    Query query = new Query();
                    query.addCriteria(Criteria.where("paymentId").is(savedPayment.getPaymentId()));

                    Update update = new Update();
                    update.set("isPaid", isPaid);

                    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
                    return reactiveMongoTemplate.findAndModify(query, update, options, Payment.class);
                });
    }

    public Mono<Payment> updatePayment(String paymentId, boolean isPaid) {
            Query query = new Query();
            query.addCriteria(Criteria.where("paymentId").is(paymentId));

            Update update = new Update();
            update.set("isPaid", isPaid);

            FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
            return reactiveMongoTemplate.findAndModify(query, update, options, Payment.class);
    }


}
