package reservation;

import events.Saga.MakeReservationEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;


@SpringBootApplication
public class ReservationApplication {


//final Sinks.Many<String> sink;
//    public ReservationApplication() {
//        this.sink = Sinks.many().multicast().onBackpressureBuffer();
//    }

    public static void main(String[] args) {
        SpringApplication.run(ReservationApplication.class, args);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }





}