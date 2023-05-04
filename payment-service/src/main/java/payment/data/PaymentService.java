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
        paymentRepository.save(payment);

        Query query = new Query();
        query.addCriteria(Criteria.where("paymentId").is(payment.getPaymentId()));

        Update update = new Update();

        if(payment.getIsPaid().isPresent()){
            System.out.println("Updating isPaid");
            update.set("isPaid", true);
        }

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Payment.class);
    }

    public Mono<Payment> createReservation(Payment payment){
        paymentRepository.save(payment);

        Query query = new Query();
        query.addCriteria(Criteria.where("paymentId").is(payment.getPaymentId()));

        Update update = new Update();

        if(payment.getIsPaid().isPresent()){
            System.out.println("Updating isPaid");
            update.set("isPaid", false);
        }

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Payment.class);
    }

    public Mono<Payment> updatePayment(Payment payment, String paymentId){
        Query query = new Query();
        query.addCriteria(Criteria.where("paymentId").is(paymentId));

        Update update = new Update();

        if(payment.getIsPaid().isPresent()){
            System.out.println("Updating isPaid");
            update.set("isPaid", true);
        }

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Payment.class);
    }



}
