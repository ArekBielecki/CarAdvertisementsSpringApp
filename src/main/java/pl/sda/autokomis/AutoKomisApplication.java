package pl.sda.autokomis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import pl.sda.autokomis.config.MongoConfiguration;

@SpringBootApplication
@Import(value = {MongoConfiguration.class})
public class AutoKomisApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoKomisApplication.class, args);
    }
}
