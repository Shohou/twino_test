package shohov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.AsyncRestOperations;
import org.springframework.web.client.AsyncRestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
public class TwinoApp {

    public static void main(String[] args) {
        SpringApplication.run(TwinoApp.class, args);
    }

    @Bean
    AsyncRestOperations restTemplate() {
        return new AsyncRestTemplate();
    }
}
