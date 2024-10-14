package com.titanic.passengers.controller;

import com.titanic.passengers.entity.Passengers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PassengersController {
    @GetMapping("/api/passengers")
    public abstract List<Passengers> getFilteredPassengers(
            @RequestParam(required = false) Boolean survived,
            @RequestParam(required = false) Passengers.PassengerClass pclass);

    @PostMapping("/api/passengers/refresh-cache")
    ResponseEntity<String> refreshCache();
}
