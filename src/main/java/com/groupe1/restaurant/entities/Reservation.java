package com.groupe1.restaurant.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity{

    private String reservationNumber;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String clientName;

    private LocalTime startTime;

    private LocalTime endTime;

    private int nbSeats;

    private LocalDate reservationDate;
}
