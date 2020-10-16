package com.lemberski.demo.placesreactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class WeatherClient {

    @Value("${weather.service.address}")
    private String weatherService;

    private WebClient webClient;

    @PostConstruct
    private void init() {
        this.webClient = WebClient.builder().baseUrl(weatherService).build();
    }

    public Mono<Map> getWeather(String city) {
        return this.webClient.get().uri("/weather/{city}", city).retrieve().bodyToMono(Map.class);
    }

}
