package com.groupe1.restaurant.services;

import com.groupe1.restaurant.dto.MenuDto;
import com.groupe1.restaurant.dto.MenuRequest;

import java.util.List;

public interface IMenuService {

    MenuDto create(MenuRequest request);

    MenuDto findById(Integer id);

    MenuDto update(MenuRequest request, Integer id);
    List<MenuDto> findAllByRestaurant(Integer restaurantId);

    List<MenuDto> findByDaysAndRestaurant(String days, Integer restaurantId);
}
