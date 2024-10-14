package com.titanic.passengers.service;

import com.titanic.passengers.entity.Passengers;
import com.titanic.passengers.repository.PassengersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PassengersServiceImplTest {

    @InjectMocks
    private PassengersServiceImpl passengersService;

    @Mock
    private PassengersRepository passengersRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setCsvFilePath("/passengers.csv");
    }

    private void setCsvFilePath(String path) {
        try {
            var field = PassengersServiceImpl.class.getDeclaredField("csvFilePath");
            field.setAccessible(true);
            field.set(passengersService, path);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadPassengerData_shouldLoadAndSavePassengers() {
        List<Passengers> mockPassengers = new ArrayList<>();
        mockPassengers.add(new Passengers(null, "John Doe", true, Passengers.PassengerClass.FIRST_CLASS));

        when(passengersRepository.saveAll(anyList())).thenReturn(mockPassengers);

        passengersService.loadPassengerData();

        verify(passengersRepository, times(1)).saveAll(anyList());
    }

    @Test
    void getFilteredPassengers_shouldReturnFilteredPassengers() {
        List<Passengers> mockPassengers = new ArrayList<>();
        mockPassengers.add(new Passengers(null, "Jane Doe", true, Passengers.PassengerClass.SECOND_CLASS));
        when(passengersRepository.findBySurvivedAndPclass(true, Passengers.PassengerClass.SECOND_CLASS)).thenReturn(mockPassengers);

        List<Passengers> result = passengersService.getFilteredPassengers(true, Passengers.PassengerClass.SECOND_CLASS);

        assertEquals(1, result.size());
        assertEquals("Jane Doe", result.get(0).getName());
    }

    @Test
    void getFilteredPassengers_shouldReturnEmptyListWhenNoPassengersFound() {
        when(passengersRepository.findBySurvivedAndPclass(false, Passengers.PassengerClass.FIRST_CLASS)).thenReturn(Collections.emptyList());

        List<Passengers> result = passengersService.getFilteredPassengers(false, Passengers.PassengerClass.FIRST_CLASS);

        assertEquals(0, result.size());
    }
}
