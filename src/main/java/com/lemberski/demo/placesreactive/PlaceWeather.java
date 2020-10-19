package com.lemberski.demo.placesreactive;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Value;

import java.util.Map;

@Value(staticConstructor = "of")
public class PlaceWeather {

    @JsonUnwrapped
    private Place place;

    @SuppressWarnings("rawtypes")
    private Map weather;

}
