package com.lemberski.demo.placesreactive;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlaceRepository extends ReactiveCrudRepository<Place, Long> {

}
