package travelagency.data;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TravelAgencyService {

    private OfferRepository offerRepository;
    private FlightRepository flightRepository;
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public TravelAgencyService(OfferRepository offerRepository, FlightRepository flightRepository, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.offerRepository = offerRepository;
        this.flightRepository = flightRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Flux<Offer> getOffers(){
        return offerRepository.findAll()
                .switchIfEmpty(Flux.empty());
    }

    public Flux<Flight> getFlights(){
        return flightRepository.findAll()
                .switchIfEmpty(Flux.empty());
    }

    public Mono<Offer> createOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public Mono<Flight> createFlight(Flight flight){
        return flightRepository.save(flight);
    }

    public Mono<Offer> deleteByOfferId(String offerId) {
        return offerRepository.findByOfferId(offerId)
                .flatMap(existingOffer -> offerRepository.delete(existingOffer)
                        .then(Mono.just(existingOffer)));
    }

    public Mono<Flight> deleteByFlightId(String flightId){
        return flightRepository.findByFlightId(flightId)
                .flatMap(existingFlight -> flightRepository.delete(existingFlight)
                        .then(Mono.just(existingFlight)));
    }

    public Mono<Offer> updateOffer(String offerId, Boolean available){
        Query query = new Query();
        query.addCriteria(Criteria.where("offerId").is(offerId));

        Update update = new Update();
        if(available != null) {
            update.set("available", available);
        }

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Offer.class);
    }

    public Mono<OfferNested> addEventOffer(OfferNested offerNested){
        Query query = new Query();
        query.addCriteria(Criteria.where("offerId").is(offerNested.getOfferId()));

        Update update = new Update();

        offerNested.getAvailable().ifPresent(available -> update.set("available", available));

        update.push("events", offerNested);

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        return reactiveMongoTemplate.findAndModify(query, update, options, OfferNested.class);
    }

    public Mono<FlightNested> addEventFlight(FlightNested flightNested){
        Query query = new Query();
        Update update = new Update();

        Integer seats = flightNested.getAvailable_seats().orElse(null);
        String event_type = flightNested.getEventType().orElse(null);

        query.addCriteria(Criteria
                .where("flightId").is(flightNested.getFlightId()));

        if(seats != null && event_type != null){
            query.addCriteria(Criteria
                    .where("available_seats").gte(seats));

            if(event_type.equals("BlockResourcesEvent")){
                update.inc("available_seats",-seats);
            }
            else if(event_type.equals("UnblockResourcesEvent")){
                update.inc("available_seats",seats);
            }
        }


        update.push("events", flightNested);

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        return reactiveMongoTemplate.findAndModify(query, update, options, FlightNested.class);
    }

}
