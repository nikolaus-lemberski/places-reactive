package com.lemberski.demo.placesreactive;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Place {

    @Id
    private Long id;
    private String name;

}
