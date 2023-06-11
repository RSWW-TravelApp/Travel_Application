package client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
public class WebClientController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Mono<String> homePage() {
        return Mono.just("flights");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Mono<String> loginPage() {
        return Mono.just("login");
    }

    @RequestMapping(value = "/flights", method = RequestMethod.GET)
    public Mono<String> flightsPage() {
        return Mono.just("flights");
    }

    @RequestMapping(value = "/offers", method = RequestMethod.GET)
    public Mono<String> offersPage() {
        return Mono.just("offers");
    }

    @RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
    public Mono<String> offerDetailsPage(@PathVariable String id) {
        return Mono.just("offerDetails");
    }

    @RequestMapping(value = "/recentChanges", method = RequestMethod.GET)
    public Mono<String> recentChangesPage() {
        return Mono.just("recentChanges");
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public Mono<String> statisticsPage() {
        return Mono.just("statistics");
    }
    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    public Mono<String> userReservationsPage() {
        return Mono.just("reservations");
    }
}
