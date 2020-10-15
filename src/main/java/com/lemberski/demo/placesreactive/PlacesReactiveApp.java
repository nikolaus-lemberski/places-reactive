package com.lemberski.demo.placesreactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PlacesReactiveApp {

    public static void main(String[] args) {
        SpringApplication.run(PlacesReactiveApp.class, args);
    }

}