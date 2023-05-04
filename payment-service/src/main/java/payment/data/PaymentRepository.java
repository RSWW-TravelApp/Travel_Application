package payment.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@EnableReactiveMongoRepositories
public interface PaymentRepository extends ReactiveMongoRepository<Payment, String> {

    Mono<Payment> findByPaymentId(String paymentID);
}
