package com.groupe1.restaurant.dto;

import com.groupe1.restaurant.entities.Restaurant;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDto {

    private Integer id;
    private String name;
    private String login;
    private String city;
    private String address;
    private String phone;
    private String type;
    private String email;
    private String description;
    private String image;
    private int rating;
    private String openingHours;
    private String closingHours;
    private int capacity;
    private List<MenuDto> menuDtoList;
    private boolean isOpen;

    public static RestaurantDto fromEntity(Restaurant restaurant){
        return RestaurantDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .city(restaurant.getCity())
                .address(restaurant.getAddress())
                .phone(restaurant.getPhone())
                .type(restaurant.getType().name())
                .email(restaurant.getEmail())
                .description(restaurant.getDescription())
                .image(restaurant.getImage())
                .rating(restaurant.getRating())
                .openingHours(restaurant.getOpeningHours().format(DateTimeFormatter.ofPattern("HH:mm")))
                .closingHours(restaurant.getClosingHours().format(DateTimeFormatter.ofPattern("HH:mm")))
                .capacity(restaurant.getCapacity())
                .menuDtoList( restaurant.getMenus()!=null ? restaurant.getMenus().stream().map(MenuDto::fromEntity).toList() : List.of())
                .isOpen(restaurant.getOpeningHours().isBefore(LocalTime.now()))
                .build();
    }

}
