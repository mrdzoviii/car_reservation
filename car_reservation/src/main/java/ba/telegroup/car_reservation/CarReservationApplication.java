package ba.telegroup.car_reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties
@ServletComponentScan
@EnableAsync
public class CarReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarReservationApplication.class, args);
    }
}
