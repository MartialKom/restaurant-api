package com.groupe1.restaurant.services;

import com.groupe1.restaurant.dto.RestaurantDto;
import com.groupe1.restaurant.dto.RestaurantLogin;
import com.groupe1.restaurant.dto.RestaurantRequest;

import java.util.List;

public interface IRestaurantService {

    RestaurantDto create(RestaurantRequest request);

    List<RestaurantDto> findAll();
    RestaurantDto update(Integer id, RestaurantRequest request);
    RestaurantDto getRestaurantById(Integer id);
    List<RestaurantDto> findAllOpenByCity(String city, String time);
    RestaurantDto findByNameAndCity(String city, String name);
    List<RestaurantDto> findByRatingAndCity(int rating, String city);
    List<RestaurantDto> findByTypeAndCity(String type, String city);
    List<RestaurantDto> findAllOpenByCity(String city);
    List<RestaurantDto> findAllByCity(String city);
    RestaurantDto findByNameAndLogin(RestaurantLogin restaurantLogin);
    void delete(Integer id);
}
