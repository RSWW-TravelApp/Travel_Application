package api_gateway.controllers;

import api_gateway.statistics.CircularList;
import events.Saga.ClientNotificationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

@CrossOrigin
@RestController
public class ApiGatewayController {
    private Map<String, String> users = new ConcurrentHashMap<>();
    private Map<String, Sinks.Many<ClientNotificationEvent>> SSEConnections = new ConcurrentHashMap<>() {
    };
    private CircularList<ConcurrentHashMap<String, Object>> recentChanges = new CircularList<>(10);

    private void sendUnicastNotification(ClientNotificationEvent event) {
        Sinks.Many<ClientNotificationEvent> connection = SSEConnections.get(event.getUserId());
        if (connection != null && users.get(event.getUserId()) != null) {
            connection.tryEmitNext(event);
        }
    }

    private void sendMulticastNotification(ClientNotificationEvent event) {
        var temp = new ConcurrentHashMap<>(SSEConnections);
        for (Sinks.Many<ClientNotificationEvent> connection : temp.values()) {
            connection.tryEmitNext(event);
        }
    }

    @Bean
    public Function<Flux<ClientNotificationEvent>, Mono<Void>> sendNotification() {
        return flux -> flux
                .doOnNext(event -> {
                    System.out.println("Notification to send: " + event.getMessage());
                    if (Objects.equals(event.getType(), "unicast")) {
                        sendUnicastNotification(event);
                    }
                    else if (Objects.equals(event.getType(), "multicast")) {
                        sendMulticastNotification(event);

                        HashMap<String, Object> properties = event.getProperties();
                        if (properties.containsKey("changes")) {
                            recentChanges.add(new ConcurrentHashMap<>(event.getProperties()));
                        }
                    }
                })
                .then();
    }

    @GetMapping(value = "/notifications/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ClientNotificationEvent> SSE(@PathVariable String userId) {
        Sinks.Many<ClientNotificationEvent> connection = SSEConnections.get(userId);
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

    @PostMapping(value = "/notifications")
    public Mono<String> sendEvent(@RequestBody ClientNotificationEvent event) {
        System.out.println("Notification to send: " + event.getMessage());
        if (Objects.equals(event.getType(), "unicast")) {
            sendUnicastNotification(event);
            return Mono.just("200");
        }
        else if (Objects.equals(event.getType(), "multicast")) {
            sendMulticastNotification(event);
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
        if (SSEConnections.get(login) != null) {
            return Mono.just("400,This user is already logged in!");
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

    @GetMapping(value = "/recentChanges")
    public Flux<ConcurrentHashMap<String, Object>> getRecentChanges() {
        return Flux.fromIterable(recentChanges.get());
    }
}
