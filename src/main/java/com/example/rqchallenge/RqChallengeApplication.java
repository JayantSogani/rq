package com.example.rqchallenge;

import com.example.rqchallenge.util.ExternalApiHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class RqChallengeApplication {

    private static final String baseUrl= "https://dummy.restapiexample.com/api/v1/";
    public static void main(String[] args) {
        SpringApplication.run(RqChallengeApplication.class, args);
    }

    @Bean
    public WebClient getWebClient() {
        return WebClient.create(baseUrl);
    }

}
