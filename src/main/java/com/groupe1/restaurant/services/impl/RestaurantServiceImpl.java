package com.groupe1.restaurant.services.impl;

import com.groupe1.restaurant.dto.RestaurantDto;
import com.groupe1.restaurant.dto.RestaurantLogin;
import com.groupe1.restaurant.dto.RestaurantRequest;
import com.groupe1.restaurant.entities.EnumRestaurantType;
import com.groupe1.restaurant.entities.Restaurant;
import com.groupe1.restaurant.exception.OperationNonPermittedException;
import com.groupe1.restaurant.repository.RestaurantRepository;
import com.groupe1.restaurant.services.IRestaurantService;
import com.groupe1.restaurant.utils.RestaurantLoginGenerator;
import com.groupe1.restaurant.validators.ObjectValidators;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RestaurantServiceImpl implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ObjectValidators<RestaurantRequest> validators;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ObjectValidators<RestaurantRequest> validators) {
        this.restaurantRepository = restaurantRepository;
        this.validators = validators;
    }

    @Override
    public RestaurantDto create(RestaurantRequest request) {
        validators.validate(request);

        Restaurant restaurant = RestaurantRequest.toEntity(request);
        restaurant = restaurantRepository.save(restaurant);
        restaurant.setLogin(RestaurantLoginGenerator.generateLogin(restaurant.getName(), restaurant.getId()));
        restaurant = restaurantRepository.save(restaurant);

        RestaurantDto restaurantDto = RestaurantDto.fromEntity(restaurant);
        restaurantDto.setLogin(restaurant.getLogin());


        return restaurantDto;
    }

    @Override
    public List<RestaurantDto> findAll() {
        return restaurantRepository.findAll().stream().map(RestaurantDto::fromEntity).toList();
    }

    @Override
    public RestaurantDto update(Integer id, RestaurantRequest request) {
        validators.validate(request);


        if(!request.getClosingHours().isAfter(request.getOpeningHours()))
            throw new OperationNonPermittedException("L'heure de fermeture doit être après l'heure d'ouverture");

        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Restaurant non trouvé"));

        restaurant.setName(request.getName());
        restaurant.setDescription(request.getDescription());
        restaurant.setAddress(request.getAddress());
        restaurant.setCity(request.getCity());
        restaurant.setPhone(request.getPhone());
        restaurant.setOpeningHours(request.getOpeningHours());
        restaurant.setClosingHours(request.getClosingHours());
        restaurant.setCapacity(request.getCapacity());
        restaurant.setType(request.getType());
        return RestaurantDto.fromEntity(restaurantRepository.save(restaurant));
    }

    @Override
    public RestaurantDto getRestaurantById(Integer id) {
        return RestaurantDto.fromEntity(restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Restaurant non trouvé")));
    }

    @Override
    public List<RestaurantDto> findAllOpenByCity(String city, String time) {
        LocalTime localTime = LocalTime.parse(time);
        return restaurantRepository.findAllOpenByCity(city, localTime).stream()
                .map(RestaurantDto::fromEntity)
                .toList();
    }

    @Override
    public RestaurantDto findByNameAndCity(String city, String name) {
        return RestaurantDto.fromEntity(restaurantRepository.findByNameAndCity(name, city).orElseThrow(()->new NoSuchElementException("aucun restaurant trouvé")));
    }

    @Override
    public List<RestaurantDto> findByRatingAndCity(int rating, String city) {
        return restaurantRepository.findByRatingAndCity(rating, city).stream()
                .map(RestaurantDto::fromEntity)
                .toList();
    }

    @Override
    public List<RestaurantDto> findByTypeAndCity(String type, String city) {
        return restaurantRepository.findByTypeAndCity(EnumRestaurantType.valueOf(type), city).stream()
                .map(RestaurantDto::fromEntity)
                .toList();
    }

    @Override
    public List<RestaurantDto> findAllOpenByCity(String city) {
        return restaurantRepository.findAllOpenByCity(city, LocalTime.now()).stream()
                .map(RestaurantDto::fromEntity)
                .toList();
    }

    @Override
    public List<RestaurantDto> findAllByCity(String city) {
        return restaurantRepository.findAllByCity(city).stream()
                .map(RestaurantDto::fromEntity)
                .toList();
    }

    @Override
    public RestaurantDto findByNameAndLogin(RestaurantLogin restaurantLogin) {
        return RestaurantDto.fromEntity(restaurantRepository.findByNameAndLogin(restaurantLogin.getRestaurantName(), restaurantLogin.getRestaurantLogin()).orElseThrow(
                () -> new EntityNotFoundException("Paramètres de connexion incorrects")
        ));
    }

    @Override
    public void delete(Integer id) {
        restaurantRepository.deleteById(id);
    }
}
