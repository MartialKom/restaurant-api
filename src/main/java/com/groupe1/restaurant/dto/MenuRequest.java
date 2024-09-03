package com.groupe1.restaurant.dto;

import com.groupe1.restaurant.entities.Dish;
import com.groupe1.restaurant.entities.EnumDays;
import com.groupe1.restaurant.entities.Menu;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {

    @NotNull(message = "Dishes should not be null")
    private List<DishRequest> dishes;

    @NotNull(message = "Days should not be empty")
    private EnumDays days;

    @NotNull(message = "Restaurant Id should not be empty")
    private Integer restaurantId;

    public static Menu toEntity(MenuRequest menuRequest){
        return Menu.builder()
                .dishes(menuRequest.getDishes().stream().map(DishRequest::toEntity).toList())
                .days(menuRequest.getDays())
                .build();
    }
}
