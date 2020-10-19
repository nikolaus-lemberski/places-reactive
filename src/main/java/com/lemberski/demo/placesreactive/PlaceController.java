package com.lemberski.demo.placesreactive;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceRepository placeRepository;
    private final WeatherService weatherService;

    @GetMapping("/places")
    Flux<Place> places() {
        return placeRepository.findAll();
    }

    @GetMapping("/places/{id}")
    Mono<ResponseEntity<PlaceWeather>> place(@PathVariable Long id) {
        return placeRepository.findById(id)
                .zipWhen(place -> weatherService.getWeather(place.getName()))
                .map(tuple -> PlaceWeather.of(tuple.getT1(), tuple.getT2()))
                .map(placeWeather -> ResponseEntity.ok(placeWeather))
                .defaultIfEmpty(ResponseEntity.notFound().build());
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
