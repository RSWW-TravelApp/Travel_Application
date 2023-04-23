package client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
public class WebClientController {

    public void onStart(String value) {
        System.out.println("Received value: " + value);
    }

    public void onError(Throwable error) {
        System.err.println("Error: " + error);
    }

    public void onComplete() {
        System.out.println("Done!");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Mono<String> homePage() {
        Mono<String> monoObject = Mono.just("flights");
        monoObject.subscribe(
                value -> onStart(value),
                error -> onError(error),
                () -> onComplete());
        return monoObject;
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
}
