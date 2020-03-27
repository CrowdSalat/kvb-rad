package de.weyrich.kvbrad;

import de.weyrich.kvbrad.model.nextbike.RootModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootApplication
public class KvbRadApplication {

    public static void main(String[] args) {
        SpringApplication.run(KvbRadApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            String url = "https://api.nextbike.net/maps/nextbike-official.json?city=14";
            RootModel model = restTemplate.getForObject(url, RootModel.class);
            System.out.println(model);
        };
    }
}
