package com.groupe1.restaurant.dto;

import com.groupe1.restaurant.entities.Reservation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    @NotNull(message = "L'ID du restaurant est requis")
    private Integer restaurantId;

    @NotNull(message = "La date de reservation est requise")
    @FutureOrPresent(message = "La date de reservation ne peut être antérieure à celle d'aujourd'hui")
    private LocalDate reservationDate;

    @NotNull(message = "L'heure de début est requise")
    private LocalTime startTime;

    @NotNull(message = "L'heure de fin est requise")
    private LocalTime endTime;

    @NotNull(message = "Le nombre de place est requis")
    @Min(value = 1, message = "Il faut au moins reserver pour une personne")
    private int nbOfPeople;

    @NotEmpty(message = "Le nom du client est requis")
    private String clientName;

    public static Reservation toEntity(ReservationRequest request) {
        return Reservation.builder()
                .startTime(request.startTime)
                .endTime(request.endTime)
                .nbSeats(request.nbOfPeople)
                .clientName(request.clientName)
                .reservationDate(request.reservationDate)
                .build();
    }
}
