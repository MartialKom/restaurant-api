package com.groupe1.restaurant.dto;

import com.groupe1.restaurant.entities.Dish;
import com.groupe1.restaurant.entities.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

    private List<Dish> dishes;
    private String days;

    public static MenuDto fromEntity(Menu menuRequest) {
        return new MenuDto(
                menuRequest.getDishes(),
                menuRequest.getDays().name()
        );
    }
}
