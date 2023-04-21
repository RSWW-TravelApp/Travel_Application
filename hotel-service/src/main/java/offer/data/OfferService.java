package offer.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class OfferService {

    private OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Mono<Offer> createOffer(Offer offer){
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

    // TODO fix the update function
    public Mono<Offer> update(String offerId, Offer offer){
        return offerRepository.findByOfferId(offerId)
                .flatMap(existingOffer -> {
                    offer.getHotel_name().ifPresent(existingOffer::setHotel_name);
                    offer.getCountry().ifPresent(existingOffer::setCountry);
                    return offerRepository.save(existingOffer);});}


    // updating the specific offer with the given parameters (null parameters - don't update the field)
    /*
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
     */

    /*
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


    public Flux<Offer> fetchOffers(int stars, double price, String room_type, int adults, int children_to_3, int children_to_10,
                                   int children_to_18, String meals, String country, LocalDate start_date,
                                   LocalDate end_date, boolean available) {

        return offerRepository.findByAllParameters(stars, price, room_type, adults, children_to_3, children_to_10,
                                                    children_to_18, meals, country, start_date, end_date, available,
                                                    Sort.by(Sort.Order.asc("price")));
    }
    }*/



}
