package com.groupe1.restaurant.dto;


import com.groupe1.restaurant.entities.Reservation;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private String reservationNumber;
    private RestaurantDto restaurant;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int nbOfPeople;
    private String clientName;

    public static ReservationDto fromEntity(Reservation reservation){
        return ReservationDto.builder()
                .reservationNumber(reservation.getReservationNumber())
                .restaurant(RestaurantDto.fromEntity(reservation.getRestaurant()))
                .reservationDate(reservation.getReservationDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .nbOfPeople(reservation.getNbSeats())
                .clientName(reservation.getClientName())
                .build();
    }
}
