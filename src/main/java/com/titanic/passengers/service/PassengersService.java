package com.titanic.passengers.service;

import com.titanic.passengers.entity.Passengers;

import java.util.List;

public interface PassengersService {
    // Automatically inserts values once the application starts
    void loadPassengerData();

    List<Passengers> getFilteredPassengers(final Boolean survived, final Passengers.PassengerClass pclass);

}
