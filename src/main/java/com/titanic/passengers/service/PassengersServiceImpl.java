package com.titanic.passengers.service;

import com.titanic.passengers.entity.Passengers;
import com.titanic.passengers.repository.PassengersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PassengersServiceImpl implements PassengersService {

    @Autowired
    private PassengersRepository passengersRepository;

    @Value("${passengers.csv.path}")
    private String csvFilePath;

    @Override
    @PostConstruct
    public void loadPassengerData() {
        List<Passengers> passengersList = readPassengersFromCSV();
        passengersRepository.saveAll(passengersList);
    }

    @Override
    public List<Passengers> getFilteredPassengers(final Boolean survived, final Passengers.PassengerClass pclass) {
        log.info("Filtering passengers - survived={}, pclass={}", survived, pclass);
        List<Passengers> passengersList = passengersRepository.findBySurvivedAndPclass(survived, pclass);

        Optional.ofNullable(passengersList)
                .ifPresentOrElse(
                        list -> log.info("{} passengers found matching the criteria.", list.size()),
                        () -> log.info("No passengers found matching the criteria.")
                );

        return passengersList;
    }

    private List<Passengers> readPassengersFromCSV() {
        log.info("Reading passengers from CSV");

        try (InputStream inputStream = getClass().getResourceAsStream(csvFilePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            List<Passengers> passengersList = br.lines()
                    .skip(1)  // Skip the header row
                    .map(this::parseLineToPassenger)
                    .collect(Collectors.toList());

            log.info("{} passengers were read from CSV", passengersList.size());
            return passengersList;

        } catch (IOException e) {
            log.error("Error reading the passengers from CSV", e);
            return List.of();
        }
    }

    private Passengers parseLineToPassenger(final String line) {
        return Optional.ofNullable(line)
                .map(l -> l.split(","))
                .filter(data -> data.length >= 3)
                .map(data -> new Passengers(null, data[0], convertSurvived(data[2]), convertPClass(data[1])))
                .orElse(null);
    }

    private boolean convertSurvived(final String survivedValue) {
        return "1".equals(survivedValue);
    }

    private Passengers.PassengerClass convertPClass(final String pclassValue) {
        return switch (pclassValue) {
            case "0" -> Passengers.PassengerClass.VIP;
            case "1" -> Passengers.PassengerClass.FIRST_CLASS;
            case "2" -> Passengers.PassengerClass.SECOND_CLASS;
            case "3" -> Passengers.PassengerClass.POPULAR;
            default -> Passengers.PassengerClass.NO_CLASS;
        };
    }
}
