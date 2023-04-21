package offer.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@EnableReactiveMongoRepositories
public interface OfferRepository extends ReactiveMongoRepository<Offer, String> {

    /*
    Flux<Offer> findByAllParameters(
            int stars,
            double price,
            String room_type,
            int adults,
            int children_to_3,
            int children_to_10,
            int children_to_18,
            String meals,
            String country,
            LocalDate start_date,
            LocalDate end_date,
            boolean available,
            Sort sort
    );
     */
    Flux<Offer>findByCountry(String country);
    Mono<Offer>findByOfferId(String offerId);

}
