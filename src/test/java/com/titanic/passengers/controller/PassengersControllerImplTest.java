package com.titanic.passengers.controller;

import com.titanic.passengers.entity.Passengers;
import com.titanic.passengers.service.PassengersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PassengersControllerImplTest {

    @InjectMocks
    private PassengersControllerImpl passengersController;

    @Mock
    private PassengersService passengersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFilteredPassengers_WithSurvivedTrue() {
        // Given
        List<Passengers> expectedPassengers = Arrays.asList(
                new Passengers(1L, "John Doe", true, Passengers.PassengerClass.FIRST_CLASS),
                new Passengers(2L, "Jane Doe", true, Passengers.PassengerClass.FIRST_CLASS)
        );
        when(passengersService.getFilteredPassengers(true, Passengers.PassengerClass.FIRST_CLASS))
                .thenReturn(expectedPassengers);

        // When
        List<Passengers> actualPassengers = passengersController.getFilteredPassengers(true, Passengers.PassengerClass.FIRST_CLASS);

        // Then
        assertEquals(expectedPassengers, actualPassengers);
    }

}
