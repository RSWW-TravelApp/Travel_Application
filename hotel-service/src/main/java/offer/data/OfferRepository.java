package offer.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends ReactiveMongoRepository<Offer, String> {

}
