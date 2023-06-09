package touroperator;

import events.CQRS.flights.UpdateFlightEvent;
import events.CQRS.offers.UpdateOfferEvent;
import events.Saga.TONotificationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class TourOperatorEvent {
    @Value("${path-to-offer-ids-file}")
    private String pathToOffersIds;
    @Value("${path-to-flight-ids-file}")
    private String pathToFlightsIds;
    @Bean
    public Function<Flux<TONotificationEvent>, Mono<Void>> receivePaymentNotif() {
        return flux -> flux
                .doOnNext(event -> System.out.println("notified of purchase. I earned: " + event.getPrice() + " From offer id: " + event.getOfferId()+ " bought by user: " + event.getUser_id()))
                .then();
    }

    @PollableBean
    public Supplier<Flux<UpdateOfferEvent>> modifyOffer() {
        return () -> {
            String id;
            Integer stars;
            String start_date;
            String end_date;
            String room_type;
            Integer max_adults;
            Integer max_children_to_3;
            Integer max_children_to_10;
            Integer max_children_to_18;
            String meals;
            Double price;
            String available;

            Random random = new Random();
            int randomNumber;

            List<String> listOfStrings;
            try {
                listOfStrings
                        = Files.readAllLines(Paths.get(pathToOffersIds));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] ids = listOfStrings.toArray(new String[0]);
            randomNumber = random.nextInt(ids.length);
            id = ids[randomNumber];

            randomNumber = random.nextInt(101);
            if(randomNumber<10) stars = random.nextInt(5) + 1;
            else stars = null;

            randomNumber = random.nextInt(101);
            if(randomNumber<10){
                int[] days = {7, 10, 14};
                long minDay = LocalDate.of(2023, 1, 1).toEpochDay();
                long maxDay = LocalDate.of(2023, 12, 31).toEpochDay();
                long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
                LocalDate startDate = LocalDate.ofEpochDay(randomDay);
                randomNumber = random.nextInt(3);
                LocalDate endDate = startDate.plusDays(days[randomNumber]);
                start_date = startDate.toString();
                end_date = endDate.toString();
            } else {
                end_date = null;
                start_date = null;
            }

            randomNumber = random.nextInt(101);
            if(randomNumber<10){
                String[] room_types = {"small", "medium", "large", "apartment", "studio"};
                randomNumber = random.nextInt(4);
                room_type = room_types[randomNumber];
            } else {
                room_type = null;
            }

            randomNumber = random.nextInt(101);
            if(randomNumber<10) max_adults = random.nextInt(4) + 1;
            else max_adults = null;

            randomNumber = random.nextInt(101);
            if(randomNumber<10) max_children_to_3 = random.nextInt(3);
            else max_children_to_3 = null;

            randomNumber = random.nextInt(101);
            if(randomNumber<10) max_children_to_10 = random.nextInt(3);
            else max_children_to_10 = null;

            randomNumber = random.nextInt(101);
            if(randomNumber<10) max_children_to_18 = random.nextInt(3);
            else max_children_to_18 = null;

            randomNumber = random.nextInt(101);
            if(randomNumber<10){
                String[] mealTypes = {"allInclusive", "onlyBreakfast", "breakfastAndDinner"};
                randomNumber = random.nextInt(3);
                meals = mealTypes[randomNumber];
            } else {
                meals = null;
            }

            randomNumber = random.nextInt(101);
            if(randomNumber<10) price = 2000 + (25000 - 2000) * random.nextDouble();
            else price = null;

            randomNumber = random.nextInt(101);
            if(randomNumber<10){
                String[] bools = {"false", "true"};
                randomNumber = random.nextInt(2);
                available = bools[randomNumber];
            } else {
                available = null;
            }

            if(stars == null && start_date == null && end_date == null && room_type == null && max_adults == null && max_children_to_3 == null && max_children_to_10 == null & max_children_to_18 == null && meals == null && price == null && available == null) return null;
            else return Flux.just(new UpdateOfferEvent(id,null,null,null,null, stars, start_date, end_date, room_type, max_adults, max_children_to_3, max_children_to_10, max_children_to_18, meals, price, available));
        };
    }

    @PollableBean
    public Supplier<Flux<UpdateFlightEvent>> modifyFLight() {
        return () -> {
            String id;
            String date;
            Integer available_seats;

            Random random = new Random();
            int randomNumber;

            List<String> listOfStrings;
            try {
                listOfStrings
                        = Files.readAllLines(Paths.get(pathToFlightsIds));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] ids = listOfStrings.toArray(new String[0]);
            randomNumber = random.nextInt(ids.length);
            id = ids[randomNumber];

            randomNumber = random.nextInt(101);
            if(randomNumber<10){
                long minDay = LocalDate.of(2023, 1, 1).toEpochDay();
                long maxDay = LocalDate.of(2023, 12, 31).toEpochDay();
                long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
                LocalDate startDate = LocalDate.ofEpochDay(randomDay);
                date = startDate.toString();
            } else date = null;

            randomNumber = random.nextInt(101);
            if(randomNumber<10) available_seats = random.nextInt(200);
            else available_seats = null;

            if(available_seats == null && date == null) return null;
            else return Flux.just(new UpdateFlightEvent(id,null,null,date,null,null,available_seats));
        };
    }
}
