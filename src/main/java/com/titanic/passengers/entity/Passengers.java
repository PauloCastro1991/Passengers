package com.titanic.passengers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passengers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean survived;

    @Enumerated(EnumType.STRING)
    private PassengerClass pclass;

    public enum PassengerClass {
        VIP, FIRST_CLASS, SECOND_CLASS, NO_CLASS, POPULAR
    }
}
