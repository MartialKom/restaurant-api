package com.groupe1.restaurant.services.impl;

import com.groupe1.restaurant.dto.DishRequest;
import com.groupe1.restaurant.dto.MenuDto;
import com.groupe1.restaurant.dto.MenuRequest;
import com.groupe1.restaurant.entities.Dish;
import com.groupe1.restaurant.entities.EnumDays;
import com.groupe1.restaurant.entities.Menu;
import com.groupe1.restaurant.entities.Restaurant;
import com.groupe1.restaurant.repository.DishRepository;
import com.groupe1.restaurant.repository.MenuRepository;
import com.groupe1.restaurant.repository.RestaurantRepository;
import com.groupe1.restaurant.services.IMenuService;
import com.groupe1.restaurant.validators.ObjectValidators;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {

    public static final String RESTAURANT_NON_TROUVE = "Restaurant non trouvé";
    private final MenuRepository menuRepository;
    private final ObjectValidators<MenuRequest> validators;
    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository, ObjectValidators<MenuRequest> validators, RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.menuRepository = menuRepository;
        this.validators = validators;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    @Transactional
    public MenuDto create(MenuRequest request) {
        validators.validate(request);

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() -> new EntityNotFoundException(RESTAURANT_NON_TROUVE));
        Menu menu = MenuRequest.toEntity(request);
        menuRepository.deleteByRestaurantAndDays(restaurant, menu.getDays());
        List<Dish> dishes =  dishRepository.saveAll(menu.getDishes());
        menu.setRestaurant(restaurant);
        menu.setDishes(dishes);
        menu = menuRepository.save(menu);

        restaurant.getMenus().add(menu);

        restaurantRepository.save(restaurant);

        return MenuDto.fromEntity(menu);
    }

    @Override
    public MenuDto findById(Integer id) {
        return MenuDto.fromEntity(menuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Menu non trouvé")));
    }

    @Override
    public MenuDto update(MenuRequest request, Integer id) {
        validators.validate(request);
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Menu non trouvé"));
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() -> new EntityNotFoundException(RESTAURANT_NON_TROUVE));
        menu.setDishes(request.getDishes().stream().map(dishRequest -> {
            return dishRepository.save(DishRequest.toEntity(dishRequest));
        }).toList());
        menu.setDays(request.getDays());
        menu.setRestaurant(restaurant);

        return MenuDto.fromEntity(menuRepository.save(menu));
    }

    @Override
    public List<MenuDto> findAllByRestaurant(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new EntityNotFoundException(RESTAURANT_NON_TROUVE));

        return menuRepository.findAllByRestaurant(restaurant).stream()
                .map(MenuDto::fromEntity)
                .toList();
    }

    @Override
    public List<MenuDto> findByDaysAndRestaurant(String days, Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new EntityNotFoundException(RESTAURANT_NON_TROUVE));
        return menuRepository.findByDaysAndRestaurant(EnumDays.valueOf(days),restaurant).stream().map(MenuDto::fromEntity).toList();
    }
}
