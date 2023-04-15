package flight;

import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;

public class FlightEvent {
    @Bean
    public Consumer<String> subscribe() {
        return received -> {
            System.out.println("I received this: " + received);
        };
    }
    //    @Bean
//    public Function<String, String> process() {
//
//        return task -> {
//            System.out.println("Processor processing " + task);
//            return task.toUpperCase(Locale.ROOT);
//        };
//    }

    @Bean
    public Function<Flux<String>, Flux<String>> process() {
        return flux ->
                flux.doOnNext(a->System.out.println("Working on " + a)).map(task -> task.toUpperCase(Locale.ROOT));
    }
    @Bean
    public Consumer<String> subscribe2() {
        return received -> {
            System.out.println("222I received this: " + received);
        };
    }
}
