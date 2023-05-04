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
//    private FlightRepository flightRepository;
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public TravelAgencyService(OfferRepository offerRepository,ReactiveMongoTemplate reactiveMongoTemplate) {
        this.offerRepository = offerRepository;
//        this.flightRepository = flightRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Flux<Offer> getOffers(){
        System.out.println("BBBBBBBBBBBBBB");

        return offerRepository.findAll()
                .switchIfEmpty(Flux.empty());
    }

//    public Flux<Flight> getFlights(){
//        return flightRepository.findAll()
//                .switchIfEmpty(Flux.empty());
//    }
//
//    public Mono<Offer> createOffer(Offer offer) {
//        return offerRepository.save(offer);
//    }
//
//    public Mono<Flight> createFlight(Flight flight){
//        return flightRepository.save(flight);
//    }
//
//    public Mono<Offer> deleteByOfferId(String offerId) {
//        return offerRepository.findByOfferId(offerId)
//                .flatMap(existingOffer -> offerRepository.delete(existingOffer)
//                        .then(Mono.just(existingOffer)));
//    }
//
//    public Mono<Flight> deleteByFlightId(String flightId){
//        return flightRepository.findByFlightId(flightId)
//                .flatMap(existingFlight -> flightRepository.delete(existingFlight)
//                        .then(Mono.just(existingFlight)));
//    }
//
//    public Mono<OfferNested> addEventOffer(OfferNested offerNested){
//        Query query = new Query();
//        query.addCriteria(Criteria.where("offerId").is(offerNested.getOfferId()));
//
//        Update update = new Update()
//                .push("events", offerNested);
//
//        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
//        return reactiveMongoTemplate.findAndModify(query, update, options, OfferNested.class);
//    }
//
//    public Mono<FlightNested> addEventFlight(FlightNested flightNested){
//        Query query = new Query();
//        query.addCriteria(Criteria.where("flightId").is(flightNested.getFlightId()));
//
//        Update update = new Update()
//                .push("events", flightNested);
//
//        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
//        return reactiveMongoTemplate.findAndModify(query, update, options, FlightNested.class);
//    }






}
