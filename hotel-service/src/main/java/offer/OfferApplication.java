package offer;

//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
//import com.mongodb.client.MongoDatabase;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import offer.data.Offer;
import offer.data.OfferRepository;
import offer.data.OfferService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Hooks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableReactiveMongoRepositories

public class OfferApplication implements CommandLineRunner{

    @Autowired
    private OfferService offerService;

    public static void main(String[] args) {
        SpringApplication.run(OfferApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        // Create a new offer
        /*
        Offer newOffer = new Offer("4550", "Hotel ABC", "image_url", "Bułgaria", "New York", 4,
                LocalDate.of(2022, 1, 2),   LocalDate.of(2022, 1, 12), "villa", 2,
                1, 2, 1, "breakfast_only", 1000.0, true);
        offerService.createOffer(newOffer).subscribe(System.out::println);
        */


        // Find by country
        /*
        System.out.println("Find by country Bułgaria: \n");
        offerService.findByCountry("Bułgaria")
            .log()
            .subscribe(
                    System.out::println,
                    error -> System.err.println("Error: " + error),
                    () -> System.out.println("Search completed Bułgaria")
            );
         */


        // Find by offerId
        /*
        System.out.println("Find by offerId 0: \n");
        offerService.findByOfferId("0")
                .log()
//                .map(Offer::getHotel_name)
                .subscribe(
                        System.out::println,
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Search completed by id 0")
                );
         */


        // Find all
        /*
        System.out.println("Find all: \n");
        offerService.getAllOffers()
                .collectList()
                .subscribe(System.out::println);
         */


        // Delete offer with id 1
        /*
        System.out.println("Delete offer with offerId 5:");
        offerService.deleteByOfferId("5")
                .subscribe(offer -> {
                    System.out.println("Offer deleted: " + offer);
                }, error -> {
                    System.err.println("Error deleting offer: " + error.getMessage());
                }, () -> {
                    System.out.println("Offer deletion completed.");
                });
         */


        // Create an offer
        /*
        Offer newOffer = new Offer("4151", "Hotel ABC", "image_url", "Bułgaria", "New York", 4,
                LocalDate.of(2022, 1, 2),   LocalDate.of(2022, 1, 12), "villa", 2,
                1, 2, 1, "breakfast_only", 1000.0, true);

        offerService.createOffer(newOffer)
                .subscribe(savedOffer -> System.out.println("Offer saved: " + savedOffer.toString()),
                        error -> System.err.println("Error occurred while saving the offer: " + error.getMessage()));
         */


        // Update an offer
        // first, find an existing offer by its offerId
//        Offer existingOffer = offerService.findByOfferId("4550").block();
//
//        // update the offer properties
//        assert existingOffer != null;
//        existingOffer.setHotel_name("HOTEL NAME UPDATE");
//        existingOffer.setCountry("COUNTRY UPDATE");
//
//        // call the updateOffer method to save the changes to the database
//        offerService.updateOffer(existingOffer)
//                .subscribe(updatedOffer -> {
//                    System.out.println("Updated offer: " + updatedOffer);
//                }, error -> {
//                    System.err.println("Error updating offer: " + error.getMessage());
//                });



//        Mono<Offer> offerToUpdate = offerService.findByOfferId("140");

//
//        Offer offerToUpdate = new Offer("4550", "HOTEL NAME UPDATE","countryValue UPDATE",
//                null, null, null, null, null, null, null,
//                null, null, null, null, null, null);

        Offer offerToUpdate = offerService.findByOfferId("4550").block();
        offerToUpdate.setHotel_name("AAAA");
        offerToUpdate.setCountry("COUNTRY UPDATE");

//        offerService.update("4550", offerToUpdate)
//                .subscribe(existingOffer -> {
//                    System.out.println("Updated offer: " + existingOffer);
//                    }, error -> {
//                    System.err.println("Error updating offer: " + error.getMessage());
//                    });






    }
}