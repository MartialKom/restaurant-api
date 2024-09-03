package com.groupe1.restaurant.services;

import com.groupe1.restaurant.dto.ReservationDto;
import com.groupe1.restaurant.dto.ReservationRequest;

import java.util.List;

public interface IReservationService {

    ReservationDto create(ReservationRequest request);

    ReservationDto update(Integer id, ReservationRequest request);

    List<ReservationDto> findByClientName(String name);

    List<ReservationDto> findByRestaurant(Integer idRestaurant);

    ReservationDto findByReservationNumber(String reservationNumber);

    ReservationDto findById(Integer id);

    public void delete(String reservationNumber);
}
