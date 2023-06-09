package travelagency.data;

import events.CQRS.flights.CreateFlightEvent;
import events.CQRS.flights.DeleteFlightEvent;
import events.CQRS.flights.UpdateFlightEvent;
import events.CQRS.offers.CreateOfferEvent;
import events.CQRS.offers.DeleteOfferEvent;
import events.CQRS.offers.UpdateOfferEvent;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import travelagency.TravelAgencyEvent;

import java.time.LocalDate;

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
        return offerRepository.save(offer)
                .flatMap(savedOffer ->
                        addEventOffer(new OfferNested(
                                savedOffer.getOfferId(),
                                savedOffer.getHotel_name(),
                                savedOffer.getImage(),
                                savedOffer.getCountry(),
                                savedOffer.getCity(),
                                savedOffer.getStars(),
                                savedOffer.getStart_date(),
                                savedOffer.getEnd_date(),
                                savedOffer.getRoom_type(),
                                savedOffer.getMax_adults(),
                                savedOffer.getMax_children_to_3(),
                                savedOffer.getMax_children_to_10(),
                                savedOffer.getMax_children_to_18(),
                                savedOffer.getMeals(),
                                savedOffer.getPrice(),
                                savedOffer.getAvailable(),
                                "CreateOffer")
                            )
                );
    }

    public Mono<Flight> createFlight(Flight flight){
        return flightRepository.save(flight)
                .flatMap(savedFlight ->
                        addEventFlight(new FlightNested(
                                savedFlight.getFlightId(),
                                savedFlight.getDeparture_country(),
                                savedFlight.getDeparture_city(),
                                savedFlight.getArrival_country(),
                                savedFlight.getArrival_city(),
                                savedFlight.getAvailable_seats(),
                                savedFlight.getDate(),
                                "CreateFlight")
                        )
                );
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
        if (offerNested.getEventType().equals("BlockResourcesEvent") || offerNested.getEventType().equals("UnblockResourcesEvent")) {
            offerNested.getAvailable().ifPresent(available -> update.set("available", available));
            query.addCriteria(Criteria
                    .where("available").is(!offerNested.getAvailable().get()));
        }
        else if(offerNested.getEventType().equals("TO_Update_Offer_Event"))
        {
            offerNested.getAvailable().ifPresent(a -> update.set("available", a.toString()));
        }

        update.push("events", offerNested);

        String type = offerNested.getEventType();
        System.out.println("Offer update/create "+type);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Offer.class).doOnNext(a -> {
            switch(type)
            {
                case ("CreateOffer"):
                    TravelAgencyEvent.sink_CQRS_offers_create.tryEmitNext(new CreateOfferEvent(a.getOfferId(),
                            a.getHotel_name(),
                            a.getImage(),
                            a.getCountry(),
                            a.getCity(),
                            a.getStars(),
                            a.getStart_date().toString(),
                            a.getEnd_date().toString(),
                            a.getRoom_type(),
                            a.getMax_adults(),
                            a.getMax_children_to_3(),
                            a.getMax_children_to_10(),
                            a.getMax_children_to_18(),
                            a.getMeals(),
                            a.getPrice(),
                            a.getAvailable().toString()));
                    break;
                case ("DeleteOffer"):
                    TravelAgencyEvent.sink_CQRS_offers_delete.tryEmitNext(new DeleteOfferEvent(a.getOfferId()));
                    break;
                default:
                    TravelAgencyEvent.sink_CQRS_offers_update.tryEmitNext(new UpdateOfferEvent(a.getOfferId(),
                            offerNested.getHotel_name().orElse(null),
                            offerNested.getImage().orElse(null),
                            offerNested.getCountry().orElse(null),
                            offerNested.getCity().orElse(null),
                            offerNested.getStars().orElse(null),
                            offerNested.getStart_date().map(LocalDate::toString).orElse(null),
                            offerNested.getEnd_date().map(LocalDate::toString).orElse(null),
                            offerNested.getRoom_type().orElse(null),
                            offerNested.getMax_adults().orElse(null),
                            offerNested.getMax_children_to_3().orElse(null),
                            offerNested.getMax_children_to_10().orElse(null),
                            offerNested.getMax_children_to_18().orElse(null),
                            offerNested.getMeals().orElse(null),
                            offerNested.getPrice().orElse(null),
                            a.getAvailable().toString()));
            }

        });
    }

    public Mono<Flight> addEventFlight(FlightNested flightNested){
        Query query = new Query();
        Update update = new Update();

        Integer seats = flightNested.getAvailable_seats().orElse(null);
        String event_type = flightNested.getEventType();

        query.addCriteria(Criteria
                .where("flightId").is(flightNested.getFlightId()));
        System.out.println("Flight update/create " + event_type);
        Integer seats_relative = null;
        if(event_type != null){

            if(seats != null && event_type.equals("BlockResourcesEvent")){
                seats_relative = -seats;
                query.addCriteria(Criteria
                        .where("available_seats").gte(seats));
                update.inc("available_seats",-seats);
            }
            else if(seats != null && event_type.equals("UnblockResourcesEvent")){
                seats_relative = seats;
                update.inc("available_seats",seats);
            }
            else if(event_type.equals("TO_Update_Flight_Event"))
            {
                flightNested.getAvailable_seats().ifPresent(a -> update.set("available_seats", a));
            }
        }



        update.push("events", flightNested);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
        return reactiveMongoTemplate.findAndModify(query, update, options, Flight.class).doOnNext(a -> {
            switch(event_type)
            {
                case "CreateFlight":
                    TravelAgencyEvent.sink_CQRS_flights_create.tryEmitNext(new CreateFlightEvent(a.getFlightId(),
                            a.getDeparture_country(),
                            a.getDeparture_city(),
                            a.getDate().toString(),
                            a.getArrival_country(),
                            a.getArrival_city(),
                            a.getAvailable_seats()));
                    break;
                case "DeleteFlight":
                    TravelAgencyEvent.sink_CQRS_flights_delete.tryEmitNext(new DeleteFlightEvent(a.getFlightId()));
                    break;
                default:
                    TravelAgencyEvent.sink_CQRS_flights_update.tryEmitNext(new UpdateFlightEvent(a.getFlightId(),
                            flightNested.getDeparture_country().orElse(null),
                            flightNested.getDeparture_city().orElse(null),
                            flightNested.getDate().map(LocalDate::toString).orElse(null),
                            flightNested.getArrival_country().orElse(null),
                            flightNested.getArrival_city().orElse(null),
                            a.getAvailable_seats()
                    ));
            }

        });
    }

}
