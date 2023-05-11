package travelagency.data;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

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

    public Mono<Offer> addEventOffer(OfferNested offerNested){
        Query query = new Query();
        query.addCriteria(Criteria.where("offerId").is(offerNested.getOfferId()));

        Update update = new Update();

        offerNested.getAvailable().ifPresent(available -> update.set("available", available));
        query.addCriteria(Criteria
                .where("available").is(!offerNested.getAvailable().get()));
        update.push("events", offerNested);

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Offer.class);
    }

    public Mono<Flight> addEventFlight(FlightNested flightNested){
        Query query = new Query();
        Update update = new Update();

        Integer seats = flightNested.getAvailable_seats().orElse(null);
        String event_type = flightNested.getEventType().orElse(null);

        query.addCriteria(Criteria
                .where("flightId").is(flightNested.getFlightId()));

        if(seats != null && event_type != null){


            if(event_type.equals("BlockResourcesEvent")){
                query.addCriteria(Criteria
                        .where("available_seats").gte(seats));
                update.inc("available_seats",-seats);
            }
            else if(event_type.equals("UnblockResourcesEvent")){
                update.inc("available_seats",seats);
            }
        }


        update.push("events", flightNested);

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Flight.class);
    }

//    public Mono<Void> ModifyResources(OfferNested offerNested, FlightNested flightNested)
//    {
//        Query query_offer = new Query();
//        Query query_flight = new Query();
//        Update update_offer = new Update();
//        Update update_flight = new Update();
//        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
//
//        Integer seats = flightNested.getAvailable_seats().orElse(null);
//        String event_type = flightNested.getEventType().orElse(null);
//
//        query_flight.addCriteria(Criteria
//                .where("flightId").is(flightNested.getFlightId()));
//        query_offer.addCriteria(Criteria
//                .where("offerId").is(offerNested.getOfferId()));
//        if(seats != null && event_type != null){
//            query_flight.addCriteria(Criteria
//                    .where("available_seats").gte(seats));
//
//            if(event_type.equals("BlockResourcesEvent")){
//                update_flight.inc("available_seats",-seats);
//            }
//            else if(event_type.equals("UnblockResourcesEvent")){
//                update_flight.inc("available_seats",seats);
//            }
//        }
//
//
//
//
//        offerNested.getAvailable().ifPresent(available -> update_offer.set("available", available));
//        query_offer.addCriteria(Criteria
//                .where("available").is(true));
//
//        return Mono.zip(reactiveMongoTemplate.findAndModify(query_flight, update_flight, options, Flight.class),reactiveMongoTemplate.findAndModify(query_offer, update_offer, options, Offer.class))
//                        .switchIfEmpty(
//                                Mono.empty()
//
//                                //
//                        )
//                        .doOnNext(
//                                event ->
//                                {
//                                    Query query_offer2 = new Query();
//                                    Query query_flight2 = new Query();
//                                    Update update_offer2 = new Update();
//                                    Update update_flight2 = new Update();
//                                    Flight flight = (Flight)event.get(0);
//                                    Offer offer = (Offer)event.get(1);
//                                    update_flight2.push("events", flight);
//                                    update_offer2.push("events", offer);
//                                    flight = (Flight)event.get(0);
//                                    offer = (Offer)event.get(1);
//                                    query_flight2.addCriteria(Criteria
//                                            .where("flightId")
//                                            .is(flight)
//                                    );
//                                    query_offer2.addCriteria(Criteria
//                                            .where("offerId")
//                                            .is(offer));
//                                    reactiveMongoTemplate.findAndModify(query_flight2, update_flight2, options, Flight.class);
//                                    reactiveMongoTemplate.findAndModify(query_offer2, update_offer2, options, Offer.class);
//                                }
//                ).then();
//    }

}
