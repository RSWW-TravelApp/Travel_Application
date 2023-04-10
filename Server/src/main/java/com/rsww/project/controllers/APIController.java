package com.rsww.project.controllers;

import com.rsww.project.dataobjects.Flight;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.*;

@CrossOrigin
@RestController
public class APIController {
    //-------------------------------- MOCK DATA --------------------------------//
    private Map<String, Flight> flights = new HashMap<>() {{
        put("1", new Flight("1", "Poland", "Gdańsk", "Germany", "Berlin", 120));
    }};

    private List<String> destinations = Arrays.asList("Poland", "Germany", "Italy", "USA", "Spain", "France");
    //-------------------------------- MOCK ENDPOINTS FOR DEVELOPMENT PURPOSES --------------------------------//
    @GetMapping("/destinations")
    public Flux<Map<String, String>> getDestinations() {
        // Communication with Flight service via RabbitMQ
        return Flux.fromIterable(destinations).map(destination -> Collections.singletonMap("name", destination));
    }




    //-------------------------------- DESIRED ENDPOINTS --------------------------------//
    @GetMapping("/")
    public Mono<String> hello() {
        // Communication with API
        return Mono.just("Hello world");
    }

    @GetMapping("/flights")
    public Flux<Flight> getFlights() {
        // Communication with Flight service via RabbitMQ
        return Flux.fromIterable(flights.values());
    }

    @GetMapping("/flights/{id}")
    public Mono<Flight> getFlight(@PathVariable String id) {
        // Communication with flight service via RabbitMQ
        Flight flight = flights.get(id);
        if (flight == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return Mono.just(flight);
    }

    @PostMapping("/reserve")
    public Mono<Flight> reserveTrip(@RequestBody @Valid Flight flight) {
        // Communication with Travel Agency service via RabbitMQ
        flight.setId(UUID.randomUUID().toString());
        flights.put(flight.getId(), flight);
        return Mono.just(flight);
    }
}
