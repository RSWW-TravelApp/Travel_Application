package offer.data;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class OfferService {

    private OfferRepository offerRepository;
    private ReactiveMongoTemplate reactiveMongoTemplate;

    private List<Pair<String,Integer>> currentRoomsTop10 = new ArrayList<>();
    private List<Pair<String,Integer>> currentHotelsTop10 = new ArrayList<>();

    public OfferService(OfferRepository offerRepository, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.offerRepository = offerRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public List<Pair<String,Integer>> getCurrentRoomsTop10() {
        return currentRoomsTop10;
    }

    public String getCurrentRoomsTop10String() {
        return getString(currentRoomsTop10);
    }

    public List<Pair<String,Integer>> getCurrentHotelsTop10() {
        return currentHotelsTop10;
    }

    public List<String> getCurrentRoomsTop10Names()
    {
        return getNames(currentRoomsTop10);
    }

    public List<String> getCurrentHotelsTop10Names()
    {
        return getNames(currentHotelsTop10);
    }

    public String getCurrentHotelsTop10String() {
        return getString(currentHotelsTop10);
    }

    private List<String> getNames(List<Pair<String, Integer>> list)
    {
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
        {
            temp.add(list.get(i).getFirst());
        }
        return temp;
    }

    private String getString(List<Pair<String,Integer>> list) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
        {
            temp.append(i + 1).append(". ").append(list.get(i).getFirst()).append("\n");
        }
        return temp.toString();
    }

    public void setCurrentRoomsTop10(List<Pair<String, Integer>> currentRoomsTop10) {
        this.currentRoomsTop10 = currentRoomsTop10;
    }

    public void setCurrentHotelsTop10(List<Pair<String, Integer>> currentHotelsTop10) {
        this.currentHotelsTop10 = currentHotelsTop10;
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

    public Mono<Offer> deleteByOfferId(String offerId) {
        return offerRepository.findByOfferId(offerId)
                .flatMap(existingOffer -> offerRepository.delete(existingOffer)
                        .then(Mono.just(existingOffer)));

    }

    // finding all offers that have specific parameters chosen in the Web filter
    public Flux<Offer> fetchOffers(String hotel_name, String image, String country, String city, Integer stars,
                                   LocalDate start_date, LocalDate end_date, String room_type, Integer max_adults,
                                   Integer max_children_to_3, Integer max_children_to_10, Integer max_children_to_18, String meals,
                                   Integer min_price, Integer max_price){

        Query query = new Query().with(Sort.by(Collections.singletonList(Sort.Order.asc("price"))));

        if(hotel_name != null){
            query.addCriteria(Criteria.where("hotel_name").is(hotel_name));
        }
        if(image != null){
            query.addCriteria(Criteria.where("image").is(image));
        }
        if(country != null){
            query.addCriteria(Criteria.where("country").is(country));
        }
        if(city != null){
            query.addCriteria(Criteria.where("city").is(city));
        }
        if(start_date != null){
            LocalDateTime startOfDay = start_date.atStartOfDay();
            LocalDateTime endOfDay = start_date.atTime(LocalTime.MAX);
            query.addCriteria(Criteria.where("start_date").gte(startOfDay).lt(endOfDay));
        }
        if(end_date != null){
            LocalDateTime startOfDay = end_date.atStartOfDay();
            LocalDateTime endOfDay = end_date.atTime(LocalTime.MAX);
            query.addCriteria(Criteria.where("end_date").gte(startOfDay).lt(endOfDay));
        }
        if(room_type != null){
            query.addCriteria(Criteria.where("room_type").is(room_type));
        }
        if(stars != null){
            query.addCriteria(Criteria.where("stars").is(stars));
        }
        if(max_adults != null){
            query.addCriteria(Criteria.where("max_adults").gte(max_adults));
        }
        if(max_children_to_3 != null){
            query.addCriteria(Criteria.where("max_children_to_3").gte(max_children_to_3));
        }
        if(max_children_to_10 != null){
            query.addCriteria(Criteria.where("max_children_to_10").gte(max_children_to_10));
        }
        if(max_children_to_18 != null){
            query.addCriteria(Criteria.where("max_children_to_18").gte(max_children_to_18));
        }
        if(meals != null) {
            query.addCriteria(Criteria.where("meals").is(meals));
        }
        if(min_price != null && max_price != null) {
            query.addCriteria(Criteria.where("price").gte(min_price).lt(max_price));
        }
        else if (min_price == null && max_price != null) {
            query.addCriteria(Criteria.where("price").lt(max_price));
        }
        else if (min_price != null && max_price == null) {
            query.addCriteria(Criteria.where("price").gte(min_price));
        }
        query.addCriteria(Criteria.where("available").is(true));

        return reactiveMongoTemplate.find(query, Offer.class);
    }


    // updating the specific offer with the given parameters (null parameters - don't update the field)
    public Mono<Offer> updateOffer(String offerId, String hotel_name, String image, String country, String city,
                                   Integer stars, LocalDate start_date, LocalDate end_date, String room_type,
                                   Integer max_adults, Integer max_children_to_3,
                                   Integer max_children_to_10, Integer max_children_to_18, String meals,
                                   Double price, Boolean available) {
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
        if(stars != null) {
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
        if(max_adults != null) {
            update.set("max_adults", max_adults);
        }
        if(max_children_to_3 != null) {
            update.set("max_children_to_3", max_children_to_3);
        }
        if(max_children_to_10 != null) {
            update.set("max_children_to_10", max_children_to_10);
        }
        if(max_children_to_18 != null) {
            update.set("max_children_to_18", max_children_to_18);
        }
        if(meals != null) {
            update.set("meals", meals);
        }
        if(price != null) {
            update.set("price", price);
        }
        if(available != null) {
            update.set("available", available);
        }

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Offer.class);
    }

}
