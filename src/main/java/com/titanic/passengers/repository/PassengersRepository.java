package com.titanic.passengers.repository;

import com.titanic.passengers.entity.Passengers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassengersRepository extends JpaRepository<Passengers, Long> {

    @Query("SELECT p FROM Passengers p " +
            "WHERE (:survived IS NULL OR p.survived = :survived) " +
            "AND (:pclass IS NULL OR p.pclass = :pclass)")
    List<Passengers> findBySurvivedAndPclass(final Boolean survived, final Passengers.PassengerClass pclass);

}
