package com.titanic.passengers.controller;

import com.titanic.passengers.entity.Passengers;
import com.titanic.passengers.service.PassengersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@EnableCaching
public class PassengersControllerCacheTest {

    @Autowired
    private PassengersControllerImpl passengersController;

    @MockBean
    private PassengersService passengersService;

    @BeforeEach
    void setUp() {
        // MockitoAnnotations.openMocks(this); // Not needed when using @MockBean
    }

    @Test
    void testGetFilteredPassengers_CacheBehavior() {
        // Given
        List<Passengers> expectedPassengers = Arrays.asList(
                new Passengers(1L, "John Doe", true, Passengers.PassengerClass.FIRST_CLASS),
                new Passengers(2L, "Jane Doe", true, Passengers.PassengerClass.SECOND_CLASS)
        );

        // Stub the service method to return the expected passengers
        when(passengersService.getFilteredPassengers(true, Passengers.PassengerClass.FIRST_CLASS))
                .thenReturn(expectedPassengers);

        // When: Call the controller method the first time
        List<Passengers> actualFirstCall = passengersController.getFilteredPassengers(true, Passengers.PassengerClass.FIRST_CLASS);

        // Then: Verify the result and that the service method was called once
        assertEquals(expectedPassengers, actualFirstCall);
        verify(passengersService, times(1)).getFilteredPassengers(true, Passengers.PassengerClass.FIRST_CLASS);

        // When: Call the controller method the second time
        List<Passengers> actualSecondCall = passengersController.getFilteredPassengers(true, Passengers.PassengerClass.FIRST_CLASS);

        // Then: Verify the result is the same, but the service method should not be called again
        assertEquals(expectedPassengers, actualSecondCall);
        verify(passengersService, times(1)).getFilteredPassengers(true, Passengers.PassengerClass.FIRST_CLASS);
    }
}
