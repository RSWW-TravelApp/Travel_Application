package offer.data;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;


@Service
public class OfferService {

    private OfferRepository offerRepository;
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public OfferService(OfferRepository offerRepository, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.offerRepository = offerRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<Offer> createOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public Flux<Offer> getAllOffers(){
        return offerRepository.findAll()
                .switchIfEmpty(Flux.empty());
    }

    public Mono<Offer> findByOfferId(String offerId){
        return offerRepository.findByOfferId(offerId)
                            .switchIfEmpty(Mono.empty());
    }

    public Flux<Offer> findByCountry(String country){
        return offerRepository.findByCountry(country)
                .switchIfEmpty(Flux.empty());
    }

    public Mono<Offer> deleteByOfferId(String offerId) {
        return offerRepository.findByOfferId(offerId)
                .flatMap(existingOffer -> offerRepository.delete(existingOffer)
                        .then(Mono.just(existingOffer)));

    }

    // finding all offers that have specific parameters chosen in the Web filter
    public Flux<Offer> findByParameters(int stars, int adults, int children_to_3, int children_to_10,
                                        int children_to_18, String meals, String room_type, double price,
                                        String country, LocalDate start_date, LocalDate end_date, String available){
        Query query = new Query()
                .with(Sort
                        .by(Collections.singletonList(Sort.Order.asc("price")))
                );
        query.addCriteria(
                Criteria.where("stars").gte(stars)
                        .and("max_adults").gte(adults)
                        .and("max_children_to_3").gte(children_to_3)
                        .and("max_children_to_10").gte(children_to_10)
                        .and("max_children_to_18").gte(children_to_18)
                        .and("price").lte(price)
                        .and("available").regex(String.valueOf(available))
        );

        if(meals != null) {
            query.addCriteria(Criteria.where("meals").regex(meals));
        }
        if(room_type != null) {
            query.addCriteria(Criteria.where("room_type").regex(room_type));
        }
        if(country != null) {
            query.addCriteria(Criteria.where("country").regex(country));
        }
        if(start_date != null){
            query.addCriteria(Criteria.where("start_date").gte(start_date));
        }
        if(end_date != null){
            query.addCriteria(Criteria.where("end_date").lte(end_date));
        }

        return reactiveMongoTemplate
                .find(query, Offer.class);
    }

    // TODO fix the update functions
    public Mono<Offer> update(Offer offer){
        return offerRepository.findByOfferId(offer.getOfferId())
                .flatMap(existingOffer -> {
                    offer.getHotel_name().ifPresent(existingOffer::setHotel_name);
                    offer.getCountry().ifPresent(existingOffer::setCountry);
                    return offerRepository.save(existingOffer);});}

    // updating the specific offer with the given parameters (null parameters - don't update the field)

    public Mono<Offer> updateOffer(Offer offer){
        return offerRepository.findByOfferId(offer.getOfferId())
                .flatMap(existingOffer -> {
                    offer.getRoom_type().ifPresent(existingOffer::setRoom_type);   //room_type -> { dbOffers.setRoom_type(room_type);  });

                    offer.getStart_date().ifPresent(existingOffer::setStart_date);

                    offer.getEnd_date().ifPresent(existingOffer::setEnd_date);

                    offer.getPrice().ifPresent(existingOffer::setPrice);

                    offer.getMax_adults().ifPresent(existingOffer::setMax_adults);

                    offer.getMax_children_to_3().ifPresent(existingOffer::setMax_children_to_3);

                    offer.getMax_children_to_10().ifPresent(existingOffer::setMax_children_to_10);

                    offer.getMax_children_to_18().ifPresent(existingOffer::setMax_children_to_18);

                    offer.getMeals().ifPresent(existingOffer::setMeals);

                    offer.getImage().ifPresent(existingOffer::setImage);

                    offer.getAvailable().ifPresent(existingOffer::setAvailable);

                    return offerRepository.save(existingOffer);
                });
    }


}
