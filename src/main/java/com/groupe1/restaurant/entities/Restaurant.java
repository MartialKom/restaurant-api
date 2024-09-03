package com.groupe1.restaurant.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private String name;
    private String login;
    @Enumerated(EnumType.STRING)
    private EnumRestaurantType type;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String city;
    private String phone;
    private String email;
    private String description;
    private String image;
    private int rating;
    @Column(nullable = false)
    private LocalTime openingHours;
    @Column(nullable = false)
    private LocalTime closingHours;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Menu> menus;
    private int capacity;
}
