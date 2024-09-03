package com.groupe1.restaurant.dto;

import com.groupe1.restaurant.entities.EnumRestaurantType;
import com.groupe1.restaurant.entities.Restaurant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {

    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "Type is required")
    private EnumRestaurantType type;

    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Address is required")
    private String address;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotEmpty(message = "City is required")
    private String city;

    @NotEmpty(message = "Phone is required")
    private String phone;

    @DateTimeFormat(pattern = "HH:mm", iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "Opening hours are required")
    private LocalTime openingHours;

    @DateTimeFormat(pattern = "HH:mm", iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "Closing hours are required")
    private LocalTime closingHours;

    @NotNull(message = "Capacity is required")
    private int capacity;


    public static Restaurant toEntity(RestaurantRequest request){
        return Restaurant.builder()
                .name(request.getName())
                .type(request.getType())
                .email(request.getEmail())
                .address(request.getAddress())
                .description(request.getDescription())
                .city(request.getCity())
                .phone(request.getPhone())
                .openingHours(request.getOpeningHours())
                .closingHours(request.getClosingHours())
                .capacity(request.getCapacity())
                .menus(List.of())
                .build();
    }

}
