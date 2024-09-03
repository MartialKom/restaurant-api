package com.groupe1.restaurant.repository;

import com.groupe1.restaurant.entities.Reservation;
import com.groupe1.restaurant.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByClientName(String clientName);

    List<Reservation> findByRestaurant(Restaurant restaurant);

    Optional<Reservation>findByReservationNumber(String reservationNumber);
}
