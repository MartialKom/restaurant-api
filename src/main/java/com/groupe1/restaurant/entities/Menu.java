package com.groupe1.restaurant.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseEntity {

    @OneToMany
    private List<Dish> dishes;

    @Enumerated(EnumType.STRING)
    private EnumDays days;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;
}
