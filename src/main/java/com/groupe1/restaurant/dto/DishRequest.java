package com.groupe1.restaurant.dto;

import com.groupe1.restaurant.entities.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishRequest {

    private String name;

    private Double price;

    public static Dish toEntity(DishRequest dishRequest){
        return new Dish(
                dishRequest.getName(),
                dishRequest.getPrice()
        );
    }
}
