package com.groupe1.restaurant.repository;

import com.groupe1.restaurant.entities.EnumDays;
import com.groupe1.restaurant.entities.Menu;
import com.groupe1.restaurant.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findAllByRestaurant(Restaurant restaurant);

    List<Menu> findByDaysAndRestaurant(EnumDays days, Restaurant restaurant);

    void deleteByRestaurantAndDays(Restaurant restaurant, EnumDays days);
}
