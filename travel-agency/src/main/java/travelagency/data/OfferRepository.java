package travelagency.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@EnableReactiveMongoRepositories
public interface OfferRepository extends ReactiveMongoRepository<Offer, String> {


    Flux<Offer>findByCountry(String country);
    Mono<Offer>findByOfferId(String offerId);

}
