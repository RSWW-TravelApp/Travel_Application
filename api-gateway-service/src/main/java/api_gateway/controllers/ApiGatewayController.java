package api_gateway.controllers;

import api_gateway.dataobjects.Hotel;
import api_gateway.dataobjects.Flight;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.*;

@CrossOrigin
@RestController
public class ApiGatewayController {
    //-------------------------------- MOCK DATA --------------------------------//
    private final List<String> destinations = Arrays.asList("Poland", "Germany", "Italy", "USA", "Spain", "France");
    private final List<String> cities = Arrays.asList("Gdansk", "New York", "Madrid", "London", "Lisbon", "Milan",
            "Warsaw", "Manchester", "Los Angeles", "Athena", "Istanbul", "Berlin", "Barcelona", "Paris", "Nice");
    private final List<String> names = Arrays.asList("Sheraton", "Kisawa Residence", "Sommerro House", "Six Senses Israel", "Royal Mansour", "Hotel Château du Grand-Lucé",
            "Capella Ubud", "The Alpina Gstaad", "Grootbos Private Nature Reserve", "Amangiri", "Nihi Sumba", "Amanyara", "InterContinental Geneva", "Rosewood Hong Kong", "Lapa Rios Ecolodge");

    private final Map<String, Flight> flights = new HashMap<>() {{
        Random rand = new Random();
        for (Integer i = 0; i<120; i++) {
            put(i.toString(), new Flight(i.toString(),
                    destinations.get(rand.nextInt(destinations.size())),
                    destinations.get(rand.nextInt(destinations.size())),
                    cities.get(rand.nextInt(cities.size())),
                    cities.get(rand.nextInt(cities.size())),
                    LocalDate.of(2023, 5, rand.nextInt(2) + 1),
                    rand.nextInt(250) + 100));
        }
    }};

    private final Map<String, Hotel> hotels = new HashMap<>() {{
        Random rand = new Random();
        for (Integer i = 0; i<120; i++) {
            put(i.toString(), new Hotel(i.toString(),
                    names.get(rand.nextInt(names.size())),
                    destinations.get(rand.nextInt(destinations.size())),
                    cities.get(rand.nextInt(cities.size())),
                    rand.nextInt(6),
                    rand.nextInt(6),
                            rand.nextInt(6),
                            rand.nextInt(6),
                            rand.nextInt(6) + 1,
                            LocalDate.of(2023, 5, rand.nextInt(2) + 1)
                    ));
        }
    }};

    private Map<String, String> users = new HashMap<>();

    @GetMapping("/offers")
    public Flux<Hotel> getTrips(
            @RequestParam(name = "dstCountry", defaultValue="") Optional<String> dstCountry,
            @RequestParam(name = "dstCity", defaultValue="") Optional<String> dstCity,
            @RequestParam(name = "srcCity", defaultValue="") Optional<String> srcCity,
            @RequestParam(name = "startDate", defaultValue="") Optional<String> startDate,
            @RequestParam(name = "ppl3plus", defaultValue="") Optional<Integer> ppl3plus,
            @RequestParam(name = "ppl10plus", defaultValue="") Optional<Integer> ppl10plus,
            @RequestParam(name = "ppl18plus", defaultValue="") Optional<Integer> ppl18plus) {
        return Flux.fromIterable(hotels.values());
    }

    @GetMapping(value = {"/offers/", "/offers/{id}"})
    public Mono<Hotel> getOfferDetails(@PathVariable Optional<String> id) {
        Hotel offer = hotels.get(id.orElse(""));
        if (offer == null) {
            return Mono.just(new Hotel());
        }
        return Mono.just(offer);
    }

    @GetMapping("/flights")
    public Flux<Flight> getFlights(@RequestParam(name = "srcCountry", defaultValue = "") Optional<String> srcCountry,
                                   @RequestParam(name = "dstCountry", defaultValue = "") Optional<String> dstCountry,
                                   @RequestParam(name = "startDate", defaultValue = "") Optional<String> startDate,
                                   @RequestParam(name = "numberOfPeople", defaultValue = "") Optional<String> numberOfPeople) {
        return Flux.fromIterable(flights.values());
    }

    @GetMapping(value = {"/flights/", "/flights/{id}"})
    public Mono<Flight> getFlight(@PathVariable Optional<String> id) {
        Flight flight = flights.get(id.orElse(""));
        if (flight == null) {
            return Mono.just(new Flight());
        }
        return Mono.just(flight);
    }

    @PostMapping(value = "/register/{login}/{password}")
    public Mono<String> register(@PathVariable String login,
                                 @PathVariable String password) {
        if (users.get(login) != null) {
            return Mono.just("400,Login already in use! Try another");
        }
        users.put(login, password);
        return Mono.just("200,Registration successful");
    }

    @GetMapping(value = "/login/{login}/{password}")
    public Mono<String> login(ServerHttpResponse response,
                              @PathVariable String login,
                              @PathVariable String password) {

        String correctPassword = users.get(login);
        if (correctPassword == null) {
            return Mono.just("404,User does not exists!");
        }
        if (!Objects.equals(correctPassword, password)) {
            return Mono.just("400,Wrong password!");
        }
        return Mono.just("200,Login successful");
    }

    @PostMapping(value = "/reserve/{offerId}/{flightId}")
    public Mono<String> reserveOffer(@PathVariable String offerId,
                                     @PathVariable String flightId) {
        System.out.println(offerId);
        System.out.println(flightId);
        return Mono.just("Successful reservation. You have 1min to purchase offer, otherwise reservation will be dropped");
    }

    @PostMapping(value = "/purchase/{offerId}/{flightId}")
    public Mono<String> purchaseOffer(@PathVariable String offerId,
                                      @PathVariable String flightId) {
        System.out.println(offerId);
        System.out.println(flightId);
        return Mono.just("Successful purchase");
    }
}
