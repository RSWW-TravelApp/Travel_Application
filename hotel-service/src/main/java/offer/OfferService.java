package offer;

import offer.data.Offer;
import offer.data.OfferRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;

@Service
public class OfferService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final OfferRepository offerRepository;

    public OfferService(ReactiveMongoTemplate reactiveMongoTemplate, OfferRepository offerRepository) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.offerRepository = offerRepository;
    }

    public Mono<Offer> createOffer(Offer offer){
        return offerRepository.save(offer);
    }

    public Flux<Offer> getAllOffers(){
        return offerRepository.findAll();
    }

    public Mono<Offer> findById(String offerId){
        return offerRepository.findById(offerId);
    }

    public Mono<Offer> deleteOffer(String offerId){
        return offerRepository.findById(offerId)
                .flatMap(existingOffer -> offerRepository.delete(existingOffer)
                        .then(Mono.just(existingOffer)));
    }

    // updating the specific offer with the given parameters (null parameters - don't update the field)
    public Mono<Offer> updateOffer(Offer offer){
        return offerRepository.findById(offer.getOfferId())
                .flatMap(dbOffers -> {
                    offer.getRoom_type().ifPresent(dbOffers::setRoom_type);   //room_type -> { dbOffers.setRoom_type(room_type);  });

                    offer.getStart_date().ifPresent(dbOffers::setStart_date);

                    offer.getEnd_date().ifPresent(dbOffers::setEnd_date);

                    offer.getPrice().ifPresent(dbOffers::setPrice);

                    offer.getMax_adults().ifPresent(dbOffers::setMax_adults);

                    offer.getMax_children_to_3().ifPresent(dbOffers::setMax_children_to_3);

                    offer.getMax_children_to_10().ifPresent(dbOffers::setMax_children_to_10);

                    offer.getMax_children_to_18().ifPresent(dbOffers::setMax_children_to_18);

                    offer.getMeals().ifPresent(dbOffers::setMeals);

                    offer.getImage().ifPresent(dbOffers::setImage);

                    offer.getAvailable().ifPresent(dbOffers::setAvailable);

                    return offerRepository.save(dbOffers);
                });
    }

    // finding all offers that have specific parameters chosen in the Web filter
    public Flux<Offer> fetchOffers(int stars, String room_type, int adults, int children_to_3, int children_to_10,
                                  int children_to_18, String meals, double price, String country, LocalDate start_date,
                                   LocalDate end_date, boolean available){

        Query query = new Query()
                .with(Sort
                        .by(Collections.singletonList(Sort.Order.asc("price")))
                );

        // parameters that have default value - 0, except "available" with default value - True
        query.addCriteria(
                Criteria.where("stars").gte(stars)
                        .and("price").gte(price)
                        .and("adults").gte(adults)
                        .and("children_to_3").gte(children_to_3)
                        .and("children_to_10").gte(children_to_10)
                        .and("children_to_18").gte(children_to_18)
                        .and("available").regex(String.valueOf(available))
        );

        // parameters that cannot have default values, either null or not null
        if(meals != null) {
            query.addCriteria(Criteria.where("meals").regex(meals));
        }
        if(room_type != null) {
            query.addCriteria(Criteria.where("room_type").regex(room_type));
        }
        if(country != null) {
            query.addCriteria(Criteria.where("country").regex(country));
        }
        if(start_date != null && end_date != null) {
            query.addCriteria(Criteria.where("start_date").lte(end_date)
                                        .and("end_date").gte(start_date));
        }
        if(start_date != null && end_date == null) {
            query.addCriteria(Criteria.where("start_date").gte(start_date));
        }
        if(end_date != null && start_date == null) {
            query.addCriteria(Criteria.where("end_date").lte(end_date));
        }

        return reactiveMongoTemplate
                .find(query, Offer.class);
    }



}
