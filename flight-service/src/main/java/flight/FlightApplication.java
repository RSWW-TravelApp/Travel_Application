package flight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveMongoRepositories
public class FlightApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightApplication.class, args);
    }
}
