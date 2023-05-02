package api_gateway.controllers;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.*;

@CrossOrigin
@RestController
public class ApiGatewayController {
    private Map<String, String> users = new HashMap<>();

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
