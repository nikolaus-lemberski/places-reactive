package com.lemberski.demo.placesreactive;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceRepository placeRepository;

    @GetMapping("/places")
    Flux<Place> places() {
        return placeRepository.findAll();
    }

    @GetMapping("/places/{id}")
    Mono<Place> place(@PathVariable Long id) {
        return placeRepository.findById(id);
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
