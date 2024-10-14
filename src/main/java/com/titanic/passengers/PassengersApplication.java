package com.titanic.passengers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EntityScan(basePackages = "com.titanic.passengers.entity")
public class PassengersApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassengersApplication.class, args);
    }

}
