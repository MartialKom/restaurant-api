package com.groupe1.restaurant.controllers;

import com.groupe1.restaurant.dto.BaseResponse;
import com.groupe1.restaurant.dto.RestaurantDto;
import com.groupe1.restaurant.dto.RestaurantLogin;
import com.groupe1.restaurant.dto.RestaurantRequest;
import com.groupe1.restaurant.entities.Restaurant;
import com.groupe1.restaurant.services.IRestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin(origins = "*")
public class RestaurantController {

    public static final String RESTAURANT_TROUVE = "Restaurant trouvé";
    private final IRestaurantService restaurantService;

    public RestaurantController(IRestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/")
    public ResponseEntity<BaseResponse<RestaurantDto>> create(@RequestBody RestaurantRequest request){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(
                new BaseResponse<>(HttpStatus.CREATED.value(), "Restaurant créé", restaurantService.create(request))
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<RestaurantDto>> update(@PathVariable Integer id, @RequestBody RestaurantRequest request){
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new BaseResponse<>(HttpStatus.OK.value(), "Restaurant modifié", restaurantService.update(id, request))
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<RestaurantDto>> getRestaurantById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new BaseResponse<>(HttpStatus.OK.value(), RESTAURANT_TROUVE, restaurantService.getRestaurantById(id))
        );
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<RestaurantDto>> retaurantLogin(@RequestBody RestaurantLogin restaurantLogin){

        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new BaseResponse<>(HttpStatus.OK.value(), RESTAURANT_TROUVE, restaurantService.findByNameAndLogin(restaurantLogin))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<RestaurantDto>> delete(@PathVariable Integer id){
        restaurantService.delete(id);
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new BaseResponse<>(HttpStatus.OK.value(), "Restaurant supprimé", null)
        );
    }

    @GetMapping("/all-open/{city}")
    public ResponseEntity<BaseResponse<List<RestaurantDto>>> findAllOpenByCity(@PathVariable String city){
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new BaseResponse<>(HttpStatus.OK.value(), "Restaurant ouvert trouvé", restaurantService.findAllOpenByCity(city))
        );
    }

    @GetMapping("/rating/{rating}/city/{city}")
    public ResponseEntity<BaseResponse<List<RestaurantDto>>> findByRatingAndCity(@PathVariable int rating, @PathVariable String city){
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new BaseResponse<>(HttpStatus.OK.value(), RESTAURANT_TROUVE, restaurantService.findByRatingAndCity(rating, city))
        );
    }

    @GetMapping("/type/{type}/city/{city}")
    public ResponseEntity<BaseResponse<List<RestaurantDto>>> findByTypeAndCity(@PathVariable String type, @PathVariable String city){
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new BaseResponse<>(HttpStatus.OK.value(), RESTAURANT_TROUVE, restaurantService.findByTypeAndCity(type, city))
        );
    }
    @GetMapping("/name/{name}/city/{city}")
    public ResponseEntity<BaseResponse<RestaurantDto>> findByNameAndCity(@PathVariable String city, @PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new BaseResponse<>(HttpStatus.OK.value(), RESTAURANT_TROUVE, restaurantService.findByNameAndCity(city, name))
        );
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<BaseResponse<List<RestaurantDto>>> getAll(@PathVariable String city){
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new BaseResponse<>(HttpStatus.OK.value(), "Liste des restaurants", restaurantService.findAllByCity(city))
        );
    }

    @GetMapping("/all-open/city/{city}/time/{time}")
    public ResponseEntity<BaseResponse<List<RestaurantDto>>> getAllOpenByCity(@PathVariable String city, @PathVariable String time){
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new BaseResponse<>(HttpStatus.OK.value(), "Liste des restaurants ouvert", restaurantService.findAllOpenByCity(city, time))
        );
    }

    @GetMapping("/")
    public ResponseEntity<BaseResponse<List<RestaurantDto>>> getAll(){
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new BaseResponse<>(HttpStatus.OK.value(), "Liste des restaurants", restaurantService.findAll())
        );
    }



}
