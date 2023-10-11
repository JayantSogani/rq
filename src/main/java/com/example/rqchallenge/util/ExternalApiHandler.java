package com.example.rqchallenge.util;

import com.example.rqchallenge.exception.DefaultFallbackException;
import com.example.rqchallenge.exception.TooManyRequestsException;
import com.example.rqchallenge.model.CreateEmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ExternalApiHandler {

    @Autowired
    private WebClient webClient;

    public <T> T get(String uri,Class<T> response) {
        final Mono<T> responseMono = webClient
                .get()
                .uri(uri)
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError,
                        clientResponse -> {
                            if (clientResponse.rawStatusCode() == 429) {
                                return Mono.error(new TooManyRequestsException("Too Many Requests"));
                            } else {
                                return Mono.error(new DefaultFallbackException("Some Exception occurred"));
                            }
                        }
                )
                .bodyToMono(response);
        return responseMono.block();
    }
    public <T> T post(String uri, CreateEmployeeRequest employee, Class<T> response) {
        final Mono<T> responseMono = webClient
                .post()
                .uri(uri)
                .body(Mono.just(employee), CreateEmployeeRequest.class)
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError,
                        clientResponse -> {
                            if (clientResponse.rawStatusCode() == 429) {
                                return Mono.error(new TooManyRequestsException("Too Many Requests"));
                            } else {
                                return Mono.error(new DefaultFallbackException("Some Exception occurred"));
                            }
                        }
                )
                .bodyToMono(response);
        return responseMono.block();
    }

    public <T> T delete(String uri, Class<T> response) {
        final Mono<T> responseMono = webClient
                .delete()
                .uri(uri)
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError,
                        clientResponse -> {
                            if (clientResponse.rawStatusCode() == 429) {
                                return Mono.error(new TooManyRequestsException("Too Many Requests"));
                            } else {
                                return Mono.error(new DefaultFallbackException("Some Exception occurred"));
                            }
                        }
                )
                .bodyToMono(response);
        return responseMono.block();
    }
}
