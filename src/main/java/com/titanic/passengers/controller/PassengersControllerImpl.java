package com.titanic.passengers.controller;

import com.titanic.passengers.entity.Passengers;
import com.titanic.passengers.service.PassengersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PassengersControllerImpl implements PassengersController {

    @Autowired
    private PassengersService passengersService;

    @Override
    @Cacheable(value = "passengersCache", key = "#survived + '-' + #pclass")
    public List<Passengers> getFilteredPassengers(
            @RequestParam(required = false) Boolean survived,
            @RequestParam(required = false) Passengers.PassengerClass pclass) {
        return passengersService.getFilteredPassengers(survived, pclass);
    }

    @Override
    @CacheEvict(value = "passengersCache", allEntries = true)
    public ResponseEntity<String> refreshCache() {
        return ResponseEntity.status(HttpStatus.OK).body("Cache refreshed successfully.");
    }
}
