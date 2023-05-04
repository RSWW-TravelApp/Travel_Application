package api_gateway.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import java.util.*;

@CrossOrigin
@RestController
public class ApiGatewayController {
    private Map<String, String> users = new HashMap<>();
    private Map<String, Sinks.Many<String>> SSEConnections = new HashMap<>();

    @GetMapping(value = "/notifications/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> SSE(@PathVariable String userId) {
        Sinks.Many<String> connection = SSEConnections.get(userId);
        if (connection != null) {
            connection.tryEmitComplete();
            SSEConnections.remove(userId);
        }
        connection = SSEConnections.computeIfAbsent(userId, k -> Sinks.many().unicast().onBackpressureBuffer());
        return connection.asFlux()
                .doFinally(signalType -> SSEConnections.remove(userId))
                .doOnCancel(() -> SSEConnections.remove(userId))
                .doOnTerminate(() -> SSEConnections.remove(userId));
    }

    @PostMapping(value = "/notifications/{userId}")
    public Mono<String> sendEvent(@PathVariable String userId,
                       @RequestBody String event) {
        Sinks.Many<String> connection = SSEConnections.get(userId);
        if (connection != null && users.get(userId) != null) {
            connection.tryEmitNext(event);
            return Mono.just("200");
        }
        return Mono.just("400");
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
    public Mono<String> login(@PathVariable String login,
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

    @PostMapping(value = "/purchase/{offerId}/{flightId}/{status}")
    public Mono<String> purchaseOffer(@PathVariable String offerId,
                                      @PathVariable String flightId,
                                      @PathVariable String status) {
        System.out.println(offerId);
        System.out.println(flightId);
        System.out.println(status);
        return Mono.just("Successful purchase");
    }
}
