package com.lemberski.demo.placesreactive;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private static final Logger LOG = LoggerFactory.getLogger(PlaceController.class);

    private final PlaceRepository placeRepository;
    private final WeatherService weatherService;

    @GetMapping("/places")
    Flux<Place> places() {
        return placeRepository.findAll();
    }

    @GetMapping("/places/{id}")
    Mono<Object> place(@PathVariable Long id) {
        return placeRepository.findById(id)
                .zipWhen(p -> weatherService.getWeather(p.getName()))
                .map(tuple -> new Object() {
                    @JsonUnwrapped
                    public Place getPlace() {
                        return tuple.getT1();
                    }

                    @SuppressWarnings("rawtypes")
                    public Map getWeather() {
                        return tuple.getT2();
                    }
                });
    }

    @PostMapping("/places")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Place> place(@RequestBody Place place) {
        return placeRepository.save(place);
    }

    @DeleteMapping("/places/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> delete(@PathVariable Long id) {
        return placeRepository.deleteById(id);
    }

}
