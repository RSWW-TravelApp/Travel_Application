package offer;

import offer.data.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

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
        System.out.println("Hotels Service Running...");

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


        // Find by parameters
        /*
        offerService.findByParameters(1, 1, 2, 1, 1, null,
                        "Studio", 1, "Bułgaria", LocalDate.of(2023, 6, 1),
                        LocalDate.of(2023, 6, 30), "true")
                .log()
                .subscribe(
                        System.out::println,
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Search completed 1 stars")
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
        // TODO fix the update function
        /*
//        Offer offerToUpdate = new Offer("2", "HOTEL NAME UPDATE","countryValue UPDATE",
//                null, null, null, null, null, null, null,
//                null, null, null, null, null, null);

        Offer offerToUpdate = offerService.findByOfferId("4550").block();
        offerToUpdate.setHotel_name("AAAA");
        offerToUpdate.setCountry("COUNTRY UPDATE");
       offerService.update("0", offerToUpdate)
               .subscribe(existingOffer -> {
                   System.out.println("Updated offer: " + existingOffer);
                   }, error -> {
                   System.err.println("Error updating offer: " + error.getMessage());
                   });
         */



        } //run


    }//class
