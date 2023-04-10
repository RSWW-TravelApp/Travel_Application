package com.rsww.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
public class FrontendController {

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
        Mono<String> monoObject = Mono.just("home");
        monoObject.subscribe(
                value -> onStart(value),
                error -> onError(error),
                () -> onComplete());
        return monoObject;
    }
    @RequestMapping(value = "/reserve", method = RequestMethod.GET)
    public Mono<String> reservePage() {
        return Mono.just("reserve");
    }

    @RequestMapping(value = "/database", method = RequestMethod.GET)
    public Mono<String> databasePage() {
        return Mono.just("flights");
    }

}
