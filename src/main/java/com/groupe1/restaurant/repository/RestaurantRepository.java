package com.groupe1.restaurant.repository;

import com.groupe1.restaurant.entities.EnumRestaurantType;
import com.groupe1.restaurant.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Optional<Restaurant> findByNameAndCity(String name, String city);

    List<Restaurant> findByTypeAndCity(EnumRestaurantType type, String city);

    List<Restaurant> findByRatingAndCity(int rating, String city);

    @Query("select r from Restaurant r where r.city = :city and r.closingHours > :time and r.openingHours < :time")
    List<Restaurant> findAllOpenByCity(@Param("city") String city, @Param("time") LocalTime time);

    List<Restaurant> findAllByCity(String city);

    @Query("select r from Restaurant r where lower(r.name) like lower(concat('%', :name, '%')) and r.login = :login")
    Optional<Restaurant> findByNameAndLogin(@Param("name") String name, @Param("login") String login);
}
