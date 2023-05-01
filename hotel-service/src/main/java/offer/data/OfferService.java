package offer.data;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    public Flux<Offer> fetchOffers(String hotel_name, String image, String country, String city, int stars,
                                   LocalDate start_date, LocalDate end_date, String room_type, int max_adults,
                                   int max_children_to_3, int max_children_to_10, int max_children_to_18, String meals){

        Query query = new Query().with(Sort.by(Collections.singletonList(Sort.Order.asc("price"))));

        System.out.println("hotel_name: " + hotel_name + ", image: " + image + ", country: " + country + ", city: "
                + city + ", stars: " + stars + ", start_date: " + start_date + ", end_date: " + end_date +
                ", room_type: "  + room_type + ", max_adults: " + max_adults + ", max_children_to_3: " +
                max_children_to_3 + ", max_children_to_10: " + max_children_to_10 + ", max_children_to_18: " +
                max_children_to_18 + ", meals: " + meals);

        if(hotel_name != null){
            System.out.println("Adding hotel_name criteria");
            query.addCriteria(Criteria.where("hotel_name").regex(hotel_name));
        }
        if(image != null){
            System.out.println("Adding image criteria");
            query.addCriteria(Criteria.where("image").regex(image));
        }
        if(country != null){
            System.out.println("Adding country criteria");
            query.addCriteria(Criteria.where("country").regex(country));
        }
        if(city != null){
            System.out.println("Adding city criteria");
            query.addCriteria(Criteria.where("city").regex(city));
        }
        if(start_date != null){
            System.out.println("Adding start_date criteria");
            query.addCriteria(Criteria.where("start_date").gte(start_date));
        }
        if(end_date != null){
            System.out.println("Adding end_date criteria");
            query.addCriteria(Criteria.where("end_date").lte(end_date));
        }
        if(room_type != null){
            System.out.println("Adding room_type criteria");
            query.addCriteria(Criteria.where("room_type").regex(room_type));
        }
        if(stars != 0){
            System.out.println("Adding stars criteria");
            query.addCriteria(Criteria.where("stars").gte(stars));
        }
        if(max_adults != 0){
            System.out.println("Adding max_adults criteria");
            query.addCriteria(Criteria.where("max_adults").gte(max_adults));
        }
        if(max_children_to_3 != 0){
            System.out.println("Adding max_children_to_3 criteria");
            query.addCriteria(Criteria.where("max_children_to_3").gte(max_children_to_3));
        }
        if(max_children_to_10 != 0){
            System.out.println("Adding max_children_to_10 criteria");
            query.addCriteria(Criteria.where("max_children_to_10").gte(max_children_to_10));
        }
        if(max_children_to_18 != 0){
            System.out.println("Adding max_children_to_18 criteria");
            query.addCriteria(Criteria.where("max_children_to_18").gte(max_children_to_18));
        }
        if(meals != null) {
            System.out.println("Adding meals criteria");
            query.addCriteria(Criteria.where("meals").regex(meals));
        }

        return reactiveMongoTemplate.find(query, Offer.class);
    }


    // updating the specific offer with the given parameters (null parameters - don't update the field)
    public Mono<Offer> updateOffer(String offerId, String hotel_name, String image, String country, String city,
                                   int stars, LocalDate start_date, LocalDate end_date, String room_type, int max_adults, int max_children_to_3,
                                   int max_children_to_10, int max_children_to_18, String meals,
                                   double price, boolean available) {
        Query query = new Query();
        query.addCriteria(Criteria.where("offerId").is(offerId));

        Update update = new Update();
        if(hotel_name != null) {
            update.set("hotel_name", hotel_name);
        }
        if(image != null) {
            update.set("image", image);
        }
        if(country != null) {
            update.set("country", country);
        }
        if(city != null) {
            update.set("city", city);
        }
        if(stars != 0) {
            update.set("stars", stars);
        }
        if(start_date != null) {
            update.set("start_date", start_date);
        }
        if(end_date != null) {
            update.set("end_date", end_date);
        }
        if(room_type != null) {
            update.set("room_type", room_type);
        }
        if(max_adults != 0) {
            update.set("max_adults", max_adults);
        }
        if(max_children_to_3 != 0) {
            update.set("max_children_to_3", max_children_to_3);
        }
        if(max_children_to_10 != 0) {
            update.set("max_children_to_10", max_children_to_10);
        }
        if(max_children_to_18 != 0) {
            update.set("max_children_to_18", max_children_to_18);
        }
        if(meals != null) {
            update.set("meals", meals);
        }
        if(price != 0) {
            update.set("price", price);
        }
        if(!available) {
            update.set("available", false);
        }

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Offer.class);
    }

}
