package com.lemberski.demo.placesreactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class WeatherService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private ReactiveCircuitBreakerFactory circuitBreakerFactory;

    @Value("${weather.service.address}")
    String weatherService;

    private WebClient webClient;
    private ReactiveCircuitBreaker circuitBreaker;

    @PostConstruct
    private void init() {
        webClient = WebClient.builder()
                .baseUrl(weatherService)
                .build();

        circuitBreaker = circuitBreakerFactory
                .create("weather");
    }

    public Mono<Map> getWeather(String city) {
        return webClient.get()
                .uri("/weather/{city}", city)
                .retrieve()
                .bodyToMono(Map.class)
                .transform(it -> circuitBreaker.run(it, throwable -> {
                    LOG.warn("Cannot fetch weather data for place {}. Cause: {}", city, throwable.getMessage());
                    return Mono.just(Map.of("error", "Weather data not available"));
                }));
    }

}
