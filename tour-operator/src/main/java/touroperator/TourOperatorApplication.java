package touroperator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TourOperatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(TourOperatorApplication.class, args);
    }
}
